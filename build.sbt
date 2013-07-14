name			:= "scaudio"

organization	:= "de.djini"

version			:= "0.3.0"

scalaVersion	:= "2.10.2"

libraryDependencies	++= Seq(
	"de.djini"	%% "scutil"	% "0.21.0"	% "compile"
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
	"-feature"
)
