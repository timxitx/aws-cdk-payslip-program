import * as cdk from '@aws-cdk/core';
import * as ecs from "@aws-cdk/aws-ecs";
import * as ecr from '@aws-cdk/aws-ecr';
import { ManagedPolicy } from '@aws-cdk/aws-iam';
import * as codebuild from '@aws-cdk/aws-codebuild';

import { Artifact, Pipeline } from '@aws-cdk/aws-codepipeline';
import { GitHubSourceAction, CodeBuildAction, EcsDeployAction} from '@aws-cdk/aws-codepipeline-actions';
import { Vpc } from '@aws-cdk/aws-ec2';
import { PipelineProject } from '@aws-cdk/aws-codebuild';
import * as ecspatterns from '@aws-cdk/aws-ecs-patterns';

const repoName = "monthly-payslip-with-cdk";
 
export class AwsCdkPayslipProgramStack extends cdk.Stack {

  public readonly urlOutput: cdk.CfnOutput;

  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    var oauthToken = cdk.SecretValue.secretsManager('github-token');

    let sourceOutput: Artifact;
    let buildOutput: Artifact;

    const vpc = new Vpc(this, "MyVpc", {
      maxAzs: 3 // Default is all AZs in region
    });

    // ECR repository
    const ecrRepository = new ecr.Repository(this, repoName, {
      repositoryName: repoName,
    });

    

    var pipelineProject = this.createPipelineProject(ecrRepository);
    pipelineProject.role?.addManagedPolicy(ManagedPolicy.fromAwsManagedPolicyName('AmazonEC2ContainerRegistryPowerUser'));

    sourceOutput = new Artifact();
    buildOutput = new Artifact();

    var githubSourceAction = this.createGithubSourceAction(sourceOutput, oauthToken);
    var buildAction = this.createBuildAction(pipelineProject, sourceOutput, buildOutput);
    var ecsDeployAction = this.createEcsDeployAction(vpc, ecrRepository, buildOutput, pipelineProject)

    var pipeline = new Pipeline(this, 'my_pipeline_', {
      stages: [
        {
          stageName: 'Source',
          actions: [githubSourceAction]
        },
        {
          stageName: 'Build',
          actions: [buildAction]
        },
        {
          stageName: 'Deploy',
          actions: [ecsDeployAction]
        },
      ],
      pipelineName: "my_pipeline",
      //artifactBucket: artifactBucket
    });

    // Create a load-balanced Fargate service and make it public
    // new ecs_patterns.ApplicationLoadBalancedFargateService(this, "MyFargateService", {
    //   cluster: cluster, // Required
    //   cpu: 256, // Default is 256
    //   desiredCount: 1, // Default is 1
    //   taskImageOptions: { image: ecs.ContainerImage.fromRegistry("amazon/amazon-ecs-sample") },
    //   memoryLimitMiB: 512, // Default is 512
    //   publicLoadBalancer: true // Default is false
    // });
  }

  private createPipelineProject(ecrRepo: ecr.Repository): codebuild.PipelineProject {
    var pipelineProject = new codebuild.PipelineProject(this, 'my-codepipeline', {
      projectName: "my-codepipeline",
      environment: {
        buildImage: codebuild.LinuxBuildImage.STANDARD_2_0,
        privileged: true
      },
      environmentVariables: {
        "ECR_REPO": {
          value: ecrRepo.repositoryUriForTag()
        }
      },
      buildSpec: codebuild.BuildSpec.fromObject({
        version: '0.2',
        phases: {
          install: {
            commands: [
              "#apt-get update -y",
            ],
            finally: [
              "echo Done installing deps"
            ],
          },
          pre_build: {
            commands: [
              'echo Logging in to Amazon ECR...',
              '$(aws ecr get-login --no-include-email)',
              'COMMIT_HASH=$(echo $CODEBUILD_RESOLVED_SOURCE_VERSION | cut -c 1-7)',
              'IMAGE_TAG=${COMMIT_HASH:=latest}'
            ],
          },
          build: {
            commands: [
              'echo Build started on `date`',
              'cd monthly-payslip',
              'echo Building Docker Image $ECR_REPO:latest',
              'docker build -f docker/Dockerfile -t $ECR_REPO:latest .',
              'echo Tagging Docker Image $ECR_REPO:latest with $ECR_REPO:$IMAGE_TAG',
              'docker tag $ECR_REPO:latest $ECR_REPO:$IMAGE_TAG',
            ],
            finally: [
              "echo Done building code"
            ],
          },
          post_build: {
            commands: [
              'echo Pushing Docker Image to $ECR_REPO:latest and $ECR_REPO:$IMAGE_TAG',
              'docker push $ECR_REPO:latest',
              'docker push $ECR_REPO:$IMAGE_TAG'
            ]
          }
        },
        artifacts: {
          files: [
            "AwsCdkPayslipProgramStack.template.json"
          ]
        },
      }),
    });
    return pipelineProject;
  }

  public createGithubSourceAction(sourceOutput: Artifact, oauthToken: cdk.SecretValue): GitHubSourceAction {
    return new GitHubSourceAction({
      actionName: 'github_source',
      owner: 'timxitx',
      repo: 'aws-cdk-payslip-program',
      oauthToken: oauthToken,
      output: sourceOutput,
      branch: 'master', // default: 'master'
    });
  }

  public createBuildAction(pipelineProject: codebuild.PipelineProject, sourceActionOutput: Artifact,
    buildOutput: Artifact): CodeBuildAction {
    var buildAction = new CodeBuildAction({
      actionName: 'Build',
      project: pipelineProject,
      input: sourceActionOutput,
      outputs: [buildOutput],

    });
    return buildAction;
  }

  public createEcsDeployAction(vpc: Vpc, ecrRepo: ecr.Repository, buildOutput: Artifact, pipelineProject: PipelineProject): EcsDeployAction {
    return new EcsDeployAction({
      actionName: 'EcsDeployAction',
      service: this.createLoadBalancedFargateService(this, vpc, ecrRepo, pipelineProject).service,
      input: buildOutput,
    })
  };

  createLoadBalancedFargateService(scope: cdk.Construct, vpc: Vpc, ecrRepository: ecr.Repository, pipelineProject: PipelineProject) {
    var fargateService = new ecspatterns.ApplicationLoadBalancedFargateService(scope, 'myLbFargateService', {
      vpc: vpc,
      memoryLimitMiB: 512,
      cpu: 256,
      assignPublicIp: true,
      // listenerPort: 8080,  
      taskImageOptions: {
        containerName: repoName,
        image: ecs.ContainerImage.fromRegistry("timxii/monthlypayslip:latest"),
        containerPort: 8080,
      },
    });
    fargateService.taskDefinition.executionRole?.addManagedPolicy((ManagedPolicy.fromAwsManagedPolicyName('AmazonEC2ContainerRegistryPowerUser')));
    return fargateService;
  }
}
