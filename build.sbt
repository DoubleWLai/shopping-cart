//import Dependencies._
//
//ThisBuild / scalaVersion := "2.13.8"
//ThisBuild / version := "2.0.0"
//ThisBuild / organization := "dev.profunktor"
//ThisBuild / organizationName := "ProfunKtor"
//
//ThisBuild / evictionErrorLevel := Level.Warn
//ThisBuild / scalafixDependencies += Libraries.organizeImports
//
//resolvers += Resolver.sonatypeRepo("snapshots")
//
//val scalafixCommonSettings =
//  inConfig(IntegrationTest)(scalafixConfigSettings(IntegrationTest))
//lazy val root = (project in file("."))
//  .settings(
//    name := "shopping-cart"
//  )
//  .aggregate(core, tests)
//
//lazy val tests = (project in file("modules/tests"))
//  .configs(IntegrationTest)
//  .settings(
//    name := "shopping-cart-test-suite",
//    scalacOptions ++= List(
//      "-Ymacro-annotations",
//      "-Yrangepos",
//      "-Wconf:cat=unused:info"
//    ),
//    testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
//    Defaults.itSettings,
//    scalafixCommonSettings,
//    libraryDependencies ++= Seq(
//      CompilerPlugin.kindProjector,
//      CompilerPlugin.betterMonadicFor,
//      CompilerPlugin.semanticDB,
//      Libraries.catsLaws,
//      Libraries.log4catsNoOp,
//      Libraries.monocleLaw,
//      Libraries.refinedScalacheck,
//      Libraries.weaverCats,
//      Libraries.weaverDiscipline,
//      Libraries.weaverScalaCheck
//    )
//  )
//  .dependsOn(core)
//
//lazy val core = (project in file("modules/core"))
//  .enablePlugins(DockerPlugin)
//  .enablePlugins(AshScriptPlugin)
//  .settings(
//    name := "shopping-cart-core",
//    Docker / packageName := "shopping-cart",
//    scalacOptions ++= List(
//      "-Ymacro-annotations",
//      "-Yrangepos",
//      "-Wconf:cat=unused:info"
//    ),
//    scalafmtOnCompile := true,
//    resolvers += Resolver.sonatypeRepo("snapshots"),
//    Defaults.itSettings,
//    scalafixCommonSettings,
//    dockerBaseImage := "openjdk:11-jre-slim-buster",
//    dockerExposedPorts ++= Seq(8080),
//    makeBatScripts := Seq(),
//    dockerUpdateLatest := true,
//    libraryDependencies ++= Seq(
//      CompilerPlugin.kindProjector,
//      CompilerPlugin.betterMonadicFor,
//      CompilerPlugin.semanticDB,
//      Libraries.cats,
//      Libraries.catsEffect,
//      Libraries.catsRetry,
//      Libraries.circeCore,
//      Libraries.circeGeneric,
//      Libraries.circeParser,
//      Libraries.circeRefined,
//      Libraries.cirisCore,
//      Libraries.cirisEnum,
//      Libraries.cirisRefined,
//      Libraries.derevoCore,
//      Libraries.derevoCats,
//      Libraries.derevoCirce,
//      Libraries.fs2,
//      Libraries.http4sDsl,
//      Libraries.http4sServer,
//      Libraries.http4sClient,
//      Libraries.http4sCirce,
//      Libraries.http4sJwtAuth,
//      Libraries.javaxCrypto,
//      Libraries.log4cats,
//      Libraries.logback % Runtime,
//      Libraries.monocleCore,
//      Libraries.newtype,
//      Libraries.redis4catsEffects,
//      Libraries.redis4catsLog4cats,
//      Libraries.refinedCore,
//      Libraries.refinedCats,
//      Libraries.skunkCore,
//      Libraries.skunkCirce,
//      Libraries.squants
//    )
//  )
//
//addCommandAlias("runLinter", ";scalafixAll --rules OrganizeImports")

ThisBuild / scalaVersion := "2.13.0"

libraryDependencies ++= Seq(
  compilerPlugin(
    "org.typelevel" %% "kind-projector" % "0.11.0" cross CrossVersion.full
  ),
  compilerPlugin(
    "org.augustjune" %% "context-applied" % "0.1.2"
  ),
  "org.typelevel" %% "cats-core" % "2.1.0",
  "org.typelevel" %% "cats-effect" % "2.1.0",
  "dev.profunktor" %% "console4cats" % "0.8.1",
  "org.manatki" %% "derevo-cats" % "0.10.5",
  "org.manatki" %% "derevo-cats-tagless" % "0.10.5",
  "co.fs2" %% "fs2-core" % "2.2.2",
  "com.olegpy" %% "meow-mtl-core" % "0.4.0",
  "com.olegpy" %% "meow-mtl-effects" % "0.4.0",
  "io.estatico" %% "newtype" % "0.4.3",
  "eu.timepit" %% "refined" % "0.9.12",
  "org.typelevel" %% "squants" % "1.6.0",
  "com.github.julien-truffaut" %% "monocle-core" % "2.0.1",
  "com.github.julien-truffaut" %% "monocle-macro" % "2.0.1"
)

scalacOptions += "-Ymacro-annotations"

//addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.4.1")
