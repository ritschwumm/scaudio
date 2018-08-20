name			:= "scaudio"
organization	:= "de.djini"
version			:= "0.135.0"

scalaVersion	:= "2.12.6"
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
	"-opt:l:inline",
	"-opt-inline-from:scaudio.**",
	"-Xfatal-warnings",
	"-Xlint"
)
javacOptions	++= Seq(
	"-source", "1.8",
	"-target", "1.8"
)

conflictManager	:= ConflictManager.strict
libraryDependencies	++= Seq(
	"de.djini"	%% "scutil-core"	% "0.145.0"	% "compile"
)

wartremoverErrors ++= Seq(
	Wart.StringPlusAny,
	Wart.EitherProjectionPartial,
	Wart.OptionPartial,
	Wart.Enumeration,
	Wart.FinalCaseClass,
	Wart.JavaConversions,
	Wart.Option2Iterable,
	Wart.TryPartial
)
