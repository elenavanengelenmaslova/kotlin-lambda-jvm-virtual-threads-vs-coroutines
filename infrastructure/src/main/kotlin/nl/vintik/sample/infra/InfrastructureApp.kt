package nl.vintik.sample.infra

import software.amazon.awscdk.App
import software.amazon.awscdk.Environment
import software.amazon.awscdk.StackProps

fun main() {
    val app = App()

    val environment = Environment.builder()
        .account(System.getenv("DEPLOY_TARGET_ACCOUNT"))
        .region(System.getenv("DEPLOY_TARGET_REGION"))
        .build()

    val stackNameTable = "Kotlin-Lambda-JVM-VirtualThreadsVsCoroutines-table"
    InfrastructureTableStack(
        app, stackNameTable, StackProps.builder()
            .stackName(stackNameTable)
            .env(environment)
            .description("Dynamo Table used for JVM example")
            .build()
    )

    val stackNameJVM = "JvmCoroutinesArm64"
    InfrastructureJvmCoroutinesArm64Stack(
        app, stackNameJVM,
        StackProps.builder()
            .stackName(stackNameJVM)
            .env(environment)
            .description("JVM example")
            .build()
    )

    val stackNameJVMC1Arm64 = "JvmVirtualThreadsArm64"
    InfrastructureJvmVirtualThreadsArm64Stack(
        app,
        stackNameJVMC1Arm64,
        StackProps.builder()
            .stackName(stackNameJVMC1Arm64)
            .env(environment)
            .description("JVM C1 Arm64 example")
            .build()
    )

    app.synth()
}
