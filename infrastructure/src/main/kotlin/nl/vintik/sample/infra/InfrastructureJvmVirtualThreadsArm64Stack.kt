package nl.vintik.sample.infra

import software.amazon.awscdk.*
import software.amazon.awscdk.services.dynamodb.Table
import software.amazon.awscdk.services.lambda.Architecture
import software.amazon.awscdk.services.lambda.Code
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.Runtime
import software.amazon.awscdk.services.logs.RetentionDays
import software.constructs.Construct

class InfrastructureJvmVirtualThreadsArm64Stack(scope: Construct, id: String, props: StackProps) : Stack(scope, id, props) {
    init {
        val productsTable = Table.fromTableArn(this, "dynamoTable", Fn.importValue("Products-JVM-VirtualThreadsVsCoroutinesTableArn"))
        val functionId = "lambdaJvmArm64VirtualThreads"
        val function = Function.Builder.create(this, functionId)
            .description("Kotlin Lambda JVM ARM64 Virtual Threads")
            .handler("nl.vintik.sample.KotlinLambda::handleRequest")
            .runtime(Runtime.JAVA_21)
            .code(Code.fromAsset("../build/dist/function-virtual-threads.zip"))
            .architecture(Architecture.ARM_64)
            .logRetention(RetentionDays.ONE_WEEK)
            .memorySize(512)
            .timeout(Duration.seconds(120))
            .build()
        productsTable.grantReadData(function)

        CfnOutput(
            this, "${functionId}-fn-arn",
            CfnOutputProps.builder()
                .value(function.functionArn)
                .description("The arn of the $functionId function")
                .exportName("${functionId}FnArn").build()
        )
    }
}