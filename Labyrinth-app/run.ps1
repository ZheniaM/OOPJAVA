##############################
##### find version of project
##############################
$xmlFile = "pom.xml"
[xml]$xmlContent = Get-Content -Path $xmlFile
$version = $xmlContent.project.version

##############################
##### run jar file
##############################
#$arguments = "-jar .\target\Labyrinth-app-$version.jar"
$command = "java -jar .\target\Labyrinth-app-$version.jar"
Write-Output "execute $command"
Invoke-Expression $command