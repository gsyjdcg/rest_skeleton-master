cd ../..
mvn sonar:sonar \
  -Dsonar.projectKey=rest_skeleton \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=7119e13bbcb50def8c123c2bf78a4d9d9854f875

