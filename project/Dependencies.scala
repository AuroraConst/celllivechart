import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbt._

object Dependencies {


  val scalajsdom  = Def.setting {
    Seq("org.scala-js" %%% "scalajs-dom" % "2.4.0")
  }

  val javatime = Def.setting{
    Seq(// https://mvnrepository.com/artifact/io.github.cquiroz/scala-java-time
     "io.github.cquiroz" %%% "scala-java-time" % "2.6.0"
    )
  }
  val scalatest   :     Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "org.scalactic" %%% "scalactic"  % DependencyVersions.scalatest,
      "org.scalatest" %%% "scalatest" % DependencyVersions.scalatest % "test"
    )
  }


  val laminar: Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "com.raquo" %%% "laminar" % DependencyVersions.laminar
    )
  }

  val upickle: Def.Initialize[Seq[ModuleID]] = Def.setting {
    Seq(
      "com.lihaoyi" %%% "upickle" % DependencyVersions.`upickle`
    )
  }

}
