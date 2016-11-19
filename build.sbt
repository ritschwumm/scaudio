name			:= "scaudio"
organization	:= "de.djini"
version			:= "0.79.0"

scalaVersion	:= "2.12.0"
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
	"-opt:box-unbox",
	"-Ywarn-unused-import",
	"-Xfatal-warnings",
	"-Xlint"
)
javacOptions	++= Seq(
	"-source", "1.7",
	"-target", "1.7"
)

conflictManager	:= ConflictManager.strict
libraryDependencies	++= Seq(
	"de.djini"	%% "scutil-core"	% "0.92.0"	% "compile"
)

wartremoverErrors ++= Seq(
	Wart.Any2StringAdd,
	Wart.EitherProjectionPartial,
	Wart.OptionPartial,
	Wart.Enumeration,
	Wart.FinalCaseClass,
	Wart.JavaConversions,
	Wart.Option2Iterable,
	Wart.TryPartial
)
