# kotlin-lambda-jvm-virtual-threads-vs-coroutines
Kotlin Lambda example on JVM contains two CDK stacks of Kotlin Lambda on JVM: JVM on with coroutines, JVM with virtual threads, and one stack for Dynamo Table. 

## Build & Deployment from local machine
### Build kotlin app
Unit tests are using Testcontainers to run DynamoDB locally. 
Ensure docker is running locally, then execute:
```
./gradlew clean build
```
### Set up CDK deployment

Install CDK (if you have not already):
```
npm install -g aws-cdk
```

If you have not set up CDK in you AWS account yet, please run (replace variables in brackets with actual values):
```
cdk bootstrap aws://[aws_account_id]/[aws_region]
```

Now deploy all stacks:
```
cdk deploy -vv --require-approval never --all
```

## Build & Deployment to AWS account from GitHub
Set up the following secrets in your GitHub project:
```
AWS_ACCOUNT_ID
AWS_ACCESS_KEY
AWS_SECRET_KEY
```
Update AWS region in `workflow-build-deploy.yml` in `.github` folder of the project
