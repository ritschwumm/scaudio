name			:= "scaudio"
organization	:= "de.djini"
version			:= "0.67.0"

scalaVersion	:= "2.11.7"
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
	"-optimize",
	"-Ywarn-unused-import",
	"-Xfatal-warnings"
)
javacOptions	++= Seq(
	"-source", "1.7",
	"-target", "1.7"
)

conflictManager	:= ConflictManager.strict
libraryDependencies	++= Seq(
	"de.djini"	%% "scutil-core"	% "0.80.0"	% "compile"
)

