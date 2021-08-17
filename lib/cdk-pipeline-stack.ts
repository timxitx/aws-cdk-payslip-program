import { Construct, SecretValue, Stack, StackProps } from '@aws-cdk/core';
import { CodePipeline, CodePipelineSource, ShellStep, SimpleSynthAction } from "@aws-cdk/pipelines";
import * as codepipeline from '@aws-cdk/aws-codepipeline';
import * as codepipeline_actions from '@aws-cdk/aws-codepipeline-actions';

/**
 * The stack that defines the application pipeline
 */
export class CdkPipelineStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const sourceArtifact = new codepipeline.Artifact();

    const pipeline = new codepipeline.Pipeline(this, 'Pipeline', {
        // The pipeline name
        pipelineName: 'MyServicePipeline',
    });

    const sourceAction = new codepipeline_actions.GitHubSourceAction({
        actionName: 'GitHub',
        owner: 'timxitx',
        repo: 'aws-cdk-payslip-program',
        oauthToken: SecretValue.secretsManager('github-token'),
        output: sourceArtifact,
        branch: 'master', // default: 'master'
      });

    //   pipeline.addStage({
    //     stageName: 'Source',
    //     actions: [sourceAction],
    //   });



    new codepipeline.Pipeline(this, 'MyPipeline', {
        stages: [
            {
                stageName: 'Source',
                actions: [sourceAction],
            },
            // {
            //     stageName: 'Build',
            //     actions: [buildAction],
            // },
        ],
    });



        

        // sourceAction: new codepipeline_actions.GitHubSourceAction({
        //     actionName: 'Github',
        //     owner: 'timxii',
        //     repo: 'aws-cdk-payslip-program',
        //     oauthToken: SecretValue.secretsManager('github-token'),
        //     output: sourceArtifact,
        //     branch: 'master', // default: 'master'
        // }),

        // synthAction: SimpleSynthAction.standardNpmSynth({
        //     sourceArtifact,
        //     cloudAssemblyArtifact,

        //     buildCommand: 'npm run build'
        // })
    

    // This is where we add the application stages
    // ...
  }
}