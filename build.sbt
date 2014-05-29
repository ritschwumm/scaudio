name			:= "scaudio"

organization	:= "de.djini"

version			:= "0.27.0"

scalaVersion	:= "2.10.4"

libraryDependencies	++= Seq(
	"de.djini"	%% "scutil-core"	% "0.43.0"	% "compile"
)

scalacOptions	++= Seq(
	"-deprecation",
	"-unchecked",
	// "-language:implicitConversions",
	// "-language:existentials",
	// "-language:higherKinds",
	// "-language:reflectiveCalls",
	// "-language:dynamics",
	// "-language:postfixOps",
	// "-language:experimental.macros"
	"-feature",
	"-optimize"
)
