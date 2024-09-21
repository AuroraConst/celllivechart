import org.scalajs.linker.interface.ModuleSplitStyle

organization := "com.example"

name := "celllivechart"

version := "0.0.1"
lazy val showJavaProperties = taskKey[Unit]("Show Java system properties")

showJavaProperties := {
  val props = System.getProperties
  println("Java System Properties:")
  props.forEach((key, value) => println(s"$key: $value"))
}

lazy val celllivechart = project.in(file("."))
  .enablePlugins(ScalaJSPlugin) // Enable the Scala.js plugin in this project
  .enablePlugins(ScalablyTypedConverterExternalNpmPlugin)
  .enablePlugins(TzdbPlugin)
  .settings(
    scalaVersion := "3.5.0",

    // Tell Scala.js that this is an application with a main method
    scalaJSUseMainModuleInitializer := true,

    /* Configure Scala.js to emit modules in the optimal way to
     * connect to Vite's incremental reload.
     * - emit ECMAScript modules
     * - emit as many small modules as possible for classes in the "livechart" package
     * - emit as few (large) modules as possible for all other classes
     *   (in particular, for the standard library)
     */
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(
          ModuleSplitStyle.SmallModulesFor(List("livechart")))
    },

    /*
     *add resolver for scalatest
     */
    resolvers += "Artima Maven Repository" at "https://repo.artima.com/releases",


    /* Depend on the scalajs-dom library.
     * It provides static types for the browser DOM APIs.
     */
    libraryDependencies ++= Dependencies.scalajsdom.value,
    libraryDependencies ++= Dependencies.laminar.value,
    libraryDependencies ++= Dependencies.javatime.value,
    libraryDependencies ++= Dependencies.upickle.value,
    libraryDependencies ++= Dependencies.scalatest.value,

    // Tell ScalablyTyped that we manage `npm install` ourselves
    externalNpm := baseDirectory.value,
    zonesFilter := {(z: String) => z == "America/Toronto"} // A function to filter what timezones are included. By default all zones are included but to reduce the size you should specify only the ones you need
  )
