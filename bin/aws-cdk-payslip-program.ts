#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from '@aws-cdk/core';
import { AwsCdkPayslipProgramStack } from '../lib/aws-cdk-payslip-program-stack';

const app = new cdk.App();
new AwsCdkPayslipProgramStack(app, 'AwsCdkPayslipProgramStack', {
    env: {
      account: '837684165413',
      region: 'us-east-2',
    }
});

app.synth();