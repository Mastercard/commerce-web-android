Releasing
=========

 1. Change the version in `gradle.properties` to a non-SNAPSHOT version.
 1. Update the `CHANGELOG.md` for the impending release.
 1. Update the `README.md` with the new version.
 1. `git commit -am "Prepare for release X.Y.Z."` (where X.Y.Z is the new version)
 1. Pending: Release the artifact to Sonatype
 1. `git tag -a X.Y.Z -m "Version X.Y.Z"` (where X.Y.Z is the new version)
 1. Update the `gradle.properties` to the next SNAPSHOT version.
 1. `git commit -am "Prepare next development version."`
 1. `git push && git push --tags`

TODO:
 1. `./gradlew clean publishJvmPublicationToMavenRepository`.
    * TODO: Implement this to run via Jenkins/Travis
 1. Visit [Sonatype Nexus](https://oss.sonatype.org/) and promote the artifact.
 1. Add prerequisite if needed for user credentials