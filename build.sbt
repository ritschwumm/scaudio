Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / versionScheme := Some("early-semver")

name			:= "scaudio"
organization	:= "de.djini"
version			:= "0.271.0"

scalaVersion	:= "3.7.3"
scalacOptions	++= Seq(
	"-feature",
	"-deprecation",
	"-unchecked",
	"-source:future",
	"-language:experimental.into",
	"-Wunused:all",
	"-Wsafe-init",
	"-Wshadow:all",
	"-Xfatal-warnings",
	"-Xkind-projector:underscores",
)
javacOptions	++= Seq(
	"-source", "11",
	"-target", "11"
)

libraryDependencies	++= Seq(
	"de.djini"	%%	"scutil-jdk"	% "0.263.0"	% "compile",
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
