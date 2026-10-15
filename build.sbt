Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / versionScheme := Some("early-semver")

name			:= "scaudio"
organization	:= "de.djini"
version			:= "0.266.0"

scalaVersion	:= "3.7.1"
scalacOptions	++= Seq(
	"-feature",
	"-deprecation",
	"-unchecked",
	"-source:future",
	"-Wunused:all",
	"-Wsafe-init",
	"-Wshadow:all",
	"-Xfatal-warnings",
	"-Xkind-projector:underscores",
)
javacOptions	++= Seq(
	"-source", "1.8",
	"-target", "1.8"
)

libraryDependencies	++= Seq(
	"de.djini"	%%	"scutil-jdk"	% "0.258.0"	% "compile",
	"io.monix"	%%	"minitest"		% "2.9.6"	% "test"
)

testFrameworks	+= new TestFramework("minitest.runner.Framework")

wartremoverErrors ++= Seq(
	Wart.AsInstanceOf,
	Wart.IsInstanceOf,
	Wart.StringPlusAny,
	Wart.ToString,
	Wart.EitherProjectionPartial,
	Wart.OptionPartial,
	Wart.TryPartial,
	Wart.Enumeration,
	Wart.FinalCaseClass,
	Wart.Option2Iterable,
	//Wart.Any,
	Wart.AnyVal,
	//Wart.Nothing,
	Wart.ArrayEquals,
	Wart.ImplicitParameter,
	Wart.LeakingSealed,
	Wart.DefaultArguments,
	Wart.Overloading,
	//Wart.IterableOps,
)
