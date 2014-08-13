name			:= "scaudio"

organization	:= "de.djini"

version			:= "0.33.0"

scalaVersion	:= "2.11.2"

libraryDependencies	++= Seq(
	"de.djini"	%% "scutil-core"	% "0.48.0"	% "compile"
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

javacOptions	++= Seq(
	"-source", "1.6",
	"-target", "1.6"
)
