import sbt._
import Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin
import com.typesafe.sbteclipse.plugin.EclipsePlugin._
import com.earldouglas.xwp.XwpJetty
import com.earldouglas.xwp.XwpPlugin._
import com.earldouglas.xwp.XwpPlugin.webappSrc
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object Build extends Build {
  val Organization = "com.tierline"
  val Name = "scalajs-react-crud"
  val Version = "0.0.1"
  val ScalaVersion = "2.11.6"
  val ScalatraVersion = "2.3.1"

  val jettySettings = jetty(options = new ForkOptions(runJVMOptions = Seq("-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000")))

  lazy val shared = project.in(file("./shared")).settings(
    scalaVersion := ScalaVersion)

  lazy val root = project.in(file(".")).
    aggregate(client, server).
    settings(
      scalacOptions ++= Seq("-unchecked", "-deprecation"),
      publish := {},
      publishLocal := {},
      addCommandAlias("compileAll", ";compile;fastOptJS"))


  lazy val server = crossPoejct.jvm.settings(
    jettySettings,
    webappSrc in webapp <<= (sourceDirectory in Compile) map {
      _ / "webapp"
    }).dependsOn(shared)

  lazy val client = crossPoejct.js.settings(
    fastOptJS in Compile := {
      val rootTarget = (baseDirectory in server).value / "src" / "main" / "webapp" / "js"
      println("fastOAptJs copy -> " + rootTarget)
      val base = (fastOptJS in Compile).value
      IO.copyFile(base.data, rootTarget / base.data.getName)
      IO.copyFile(base.data, rootTarget / (base.data.getName + ".map"))
      base
    }).dependsOn(shared)

  lazy val crossPoejct = crossProject.in(file(".")).
    settings(
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      EclipseKeys.useProjectId := false,
      EclipseKeys.withSource := true,
      EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
      ).jvmSettings(
        name := "server",
        libraryDependencies ++= Seq(
          "com.lihaoyi" %% "autowire" % "0.2.5",
          "com.lihaoyi" %% "upickle" % "0.2.7",
          //"me.chrons" %%% "boopickle" % "0.1.3",
          "com.lihaoyi" %% "scalatags" % "0.5.2",
          "org.scalatra" %% "scalatra" % ScalatraVersion,
          "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
          "org.json4s" %% "json4s-native" % "3.2.11" % "test",
//          "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
          "ch.qos.logback" % "logback-classic" % "1.0.12" % "runtime",
          "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided;test")
          ).
        jsSettings(
          name := "client",
          scalaVersion := ScalaVersion,
          libraryDependencies ++= Seq(
            "com.lihaoyi" %%% "autowire" % "0.2.5",
            //"org.scala-lang.modules" %% "scala-async" % "0.9.2",
            "com.lihaoyi" %%% "upickle" % "0.2.7",
            //"me.chrons" %%% "boopickle" % "0.1.3",
            //"org.scala-js" %%% "scalajs-dom" % "0.8.0",
            //"be.doeraene" %%% "scalajs-jquery" % "0.8.0",
            //"com.github.japgolly.scalajs-react" %%% "core" % "0.9.0",
            //"com.github.japgolly.scalacss" %%% "core" % "0.2.0",
            //"com.github.japgolly.scalacss" %%% "ext-react" % "0.2.0",
            "com.lihaoyi" %%% "scalatags" % "0.5.2"),
          scalaJSStage in Global := FastOptStage,
          jsDependencies += "org.webjars" % "react" % "0.12.2" / "react-with-addons.js" commonJSName "React",
          skip in packageJSDependencies := false)

}