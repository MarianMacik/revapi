void checkVersion(File pom, String... versions) throws Exception {
    int versionCount = 0;
    boolean found = false;
    pom.eachLine { line ->
        line = line.trim();

        if (line.startsWith("<version>") && versionCount < versions.length) {
            String version = versions[versionCount++];
            assert line.equals("<version>" + version + "</version>") :
                    "The " + versionCount + "th version tag in v2 pom (" + pom.getAbsolutePath() + ") should have" +
                            " been changed to '" + version  + "' but the line reads: " + line;
            found = true;
            return;
        }
    }

    if (!found) {
        throw new AssertionError("Failed to find the <version> tag in v2 pom.xml (" + pom.getAbsolutePath() + ")");
    }
}

File topDir = new File("target/it/build/version-handling-multimodule");
if (!topDir.exists()) {
    //the top level build might be running
    String path = "revapi-maven-plugin/" + topDir.getPath()
    topDir = new File(path)
}
File topPom = new File(topDir, "pom.xml");
File v2aDir = new File(topDir, "a");
File v2aPom = new File(v2aDir, "pom.xml");
File v2bDir = new File(topDir, "b");
File v2bPom = new File(v2bDir, "pom.xml");

checkVersion(topPom, "2.0.0");
checkVersion(v2aPom, "2.0.0", "2.0.0");
checkVersion(v2bPom, "2.0.0", "2.0.0");
