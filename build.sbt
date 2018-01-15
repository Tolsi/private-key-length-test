name := "private-key-length-test"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies += "org.whispersystems" % "curve25519-java" % "0.4.1"
libraryDependencies += "org.scorexfoundation" %% "scrypto" % "1.2.0"
resolvers += "Bintray ethereum repo" at "https://dl.bintray.com/ethereum/maven/"
libraryDependencies += "org.ethereum" % "ethereumj-core" % "1.5.0-RELEASE" exclude("io.netty", "netty-all")
