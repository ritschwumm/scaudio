name			:= "scaudio"
organization	:= "de.djini"
version			:= "0.177.0"

scalaVersion	:= "2.13.3"
scalacOptions	++= Seq(
	"-feature",
	"-deprecation",
	"-unchecked",
	"-Werror",
	"-Xlint",
	"-opt:l:method",
	"-opt:l:inline",
	"-opt-inline-from:scaudio.**",
)
javacOptions	++= Seq(
	"-source", "1.8",
	"-target", "1.8"
)

conflictManager		:= ConflictManager.strict withOrganization "^(?!(org\\.scala-lang|org\\.scala-js)(\\..*)?)$"
libraryDependencies	++= Seq(
	"de.djini"		%% "scutil-jdk"	% "0.185.0"	% "compile",
	"org.specs2"	%%	"specs2-core"	% "4.10.5"	% "test"
)

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
	Wart.JavaConversions,
	Wart.Option2Iterable,
	Wart.JavaSerializable,
	//Wart.Any,
	Wart.AnyVal,
	//Wart.Nothing,
	Wart.ArrayEquals,
	Wart.ImplicitParameter,
	Wart.ExplicitImplicitTypes,
	Wart.LeakingSealed,
	Wart.DefaultArguments,
	Wart.Overloading,
	//Wart.PublicInference,
	Wart.TraversableOps,
)
