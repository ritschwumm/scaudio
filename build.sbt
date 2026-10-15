Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / versionScheme := Some("early-semver")

name			:= "scaudio"
organization	:= "de.djini"
version			:= "0.278.0"

scalaVersion	:= "3.10.0"
scalacOptions	++= Seq(
	"-preview",
	"-feature",
	"-deprecation",
	"-unchecked",
	"-source:future",
	"-Wunused:all",
	"-Wsafe-init",
	"-Wshadow:all",
	"-Wtostring-interpolated",
	"-Werror",
	"-Xkind-projector:underscores",
	"-Wconf:id=E028:silent",
)
javacOptions	++= Seq(
	"-source", "11",
	"-target", "11"
)

libraryDependencies	++= Seq(
	"de.djini"	%%	"scutil-jdk"	% "0.270.0"	% "compile",
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
	Wart.LeakingSealed,
	Wart.DefaultArguments,
	Wart.Overloading,
	//Wart.IterableOps,
)
