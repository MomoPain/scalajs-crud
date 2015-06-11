import sbt._
import Keys._
import org.scalatra.sbt._
import org.scalatra.sbt.PluginKeys._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import com.earldouglas.xwp.XwpPlugin._
import java.io.{File, FileOutputStream}
import java.nio.file._

object Build extends Build {
  val Organization = "com.tierline"
  val Name = "scalajs-react-crud"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.11.6"
  val ScalatraVersion = "2.3.0"
  val Resolvers = Seq(Resolver.sonatypeRepo("public"),    
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  )

  lazy val shared = Project(
    "shared",
    file("./shared"),
    settings = Seq(
      scalaVersion := ScalaVersion,
      libraryDependencies ++= Seq(
        "com.lihaoyi" %%% "autowire" % "0.2.5",
        "me.chrons" %%% "boopickle" % "0.1.3",
        "com.lihaoyi" %%% "utest" % "0.3.1",
        "org.webjars" % "font-awesome" % "4.3.0-1" % Provided,
        "org.webjars" % "bootstrap" % "3.3.2" % Provided
      )
    )
  )

  lazy val client = Project(
    "client",
    file("client"),
    settings = Seq(
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers in ThisBuild ++= Resolvers,
      libraryDependencies ++= Seq(
        "com.lihaoyi" %%% "autowire" % "0.2.5",
//        "com.lihaoyi" %%% "upickle" % "0.2.7",
        "me.chrons" %%% "boopickle" % "0.1.3",
        //"com.lihaoyi" %%% "scalarx" % "0.2.8",
        //"fr.iscpif" %%% "scaladget" % "0.5.0-SNAPSHOT",
        "org.scala-js" %%% "scalajs-dom" % "0.8.0",
        "be.doeraene" %%% "scalajs-jquery" % "0.8.0",
        "com.github.japgolly.scalacss" %%% "core" % "0.2.0",
        "com.github.japgolly.scalacss" %%% "ext-react" % "0.2.0",
        "com.lihaoyi" %%% "scalatags" % "0.5.2"
      ),
      jsDependencies += "org.webjars" % "react" % "0.12.2" / "react-with-addons.js" commonJSName "React"
    )
  ).dependsOn(shared) enablePlugins (ScalaJSPlugin)

  lazy val server = Project(
    "server",
    file("server"),
    settings = ScalatraPlugin.scalatraWithJRebel ++ Seq(
      organization := Organization,
      name := Name,
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers ++= Resolvers,
      javaOptions in container ++= Seq(
        "-Xdebug",
        "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000"
      ),
      libraryDependencies ++= Seq(
        "com.lihaoyi" %% "autowire" % "0.2.5",
//        "com.lihaoyi" %% "upickle" % "0.2.7",
        "me.chrons" %%% "boopickle" % "0.1.3",        
        "com.lihaoyi" %% "scalatags" % "0.5.2",
        "org.scalatra" %% "scalatra" % ScalatraVersion,
        "org.scalatra" %% "scalatra-specs2" % ScalatraVersion % "test",
        "ch.qos.logback" % "logback-classic" % "1.0.12" % "runtime",
        "javax.servlet" % "javax.servlet-api" % "3.1.0" % "container;provided;test"
      )
    ) ++ jetty(options = new ForkOptions(runJVMOptions = Seq("-Xdebug","-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000")))
  ).dependsOn(shared)

  lazy val go = taskKey[Unit]("go")

  lazy val bootstrap = Project(
    "bootstrap",
    file("target/bootstrap"),
    settings = Seq(
      version := Version,
      scalaVersion := ScalaVersion,
      (go <<= (fastOptJS in client in Compile, resourceDirectory in client in Compile, target in server in Compile) map { (ct, r, st) =>
        copy(ct, r, new File(st,"webapp"))
      }) 
      ,
     javaOptions in container ++= Seq(
        "-Xdebug",
        "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000"
      )
    )
  ) dependsOn(client, server) 


  private def copy(clientTarget: Attributed[File], resources: File, webappServerTarget: File) = {
    clientTarget.map { ct =>
      recursiveCopy(new File(resources, "webapp"), webappServerTarget)
      recursiveCopy(ct, new File(webappServerTarget, "js/" + ct.getName))
    }
  }

  private def recursiveCopy(from: File, to: File): Unit = {
    if (from.isDirectory) {
      to.mkdirs()
      for {
        f ← from.listFiles()
      } recursiveCopy(f, new File(to, f.getName))
    }
    else if (!to.exists() || from.lastModified() > to.lastModified) {
      println(s"Copy file $from to $to ")
      from.getParentFile.mkdirs
      IO.copyFile(from, to, preserveLastModified = true)
    }
  }

}