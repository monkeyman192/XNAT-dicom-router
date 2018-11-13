# README #

You can build this XNAT plugin with the following command (note that on Windows you should call 'gradlew' instead of './gradlew'):
```
#!bash

./gradlew clean jar publishToMavenLocal
```

Additionally, authorized XNAT developers can include the tasks 'publishMavenJavaPublicationToMavenRepository publishToMavenLocal' to push the plugin up to the XNAT maven server (authentication required). The full command would look like:
```
#!bash

./gradlew clean jar publishMavenJavaPublicationToMavenRepository publishToMavenLocal
```

