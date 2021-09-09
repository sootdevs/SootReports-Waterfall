<div align=center>
  <img src="https://cloud.sootmc.com/images/sootreportswebhook.png">
  
  # SootReports-Waterfall
  A custom player/bug reports and suggestions plugin for the Waterfall proxy. Programmed for SootMC.
  
  <img src="https://img.shields.io/badge/language-java-%23ED8B00">
  <img src="https://img.shields.io/badge/license-GPL--3.0-%23ED8B00">
  <img src="https://github.com/sootdevs/SootReports-Waterfall/workflows/SootReports-Waterfall/badge.svg">
</div>

## Installation
### Prerequisites
Please ensure you have the following before continuing.
- `Waterfall` running on **at least** `Java 11`.
- `1` or more Minecraft servers connected to Waterfall.
- `1` or more Discord webhooks in a server connected to a channel.
- `1` or more Discord roles in the ***same server*** as the webhooks.

### Installing
1. Download the latest ***sucessful*** artifact from the [Actions](https://github.com/sootdevs/SootReports-Waterfall/actions/workflows/sootreports-waterfall-build.yml) tab.
2. Extract the JAR file from the downloaded ZIP archive, to the `plugins` folder in your proxy's main directory.
3. Start the proxy, wait for it to finish starting, then stop the proxy. **This is to generate the default configuration file.**
4. Open the plugin configuration file (`config.yml`) for editing in `plugins/SootReports-Waterfall`.
5. Fill out ***all*** the config values using the corresponding prerequisites. ***There are NO optional config values.***
6. Start the proxy.

## Compiling
If you wish to compile the plugin yourself, the prerequisites and procedure to do so are listed below.

### Prerequisites
Please ensure you have the following before continuing.
- Preferably a `Linux`/`WSL` installation, or `Git Bash on Windows`.
  - `Git Bash on Windows` should only be used if you are **COMPLETELY UNABLE** to use `Linux` or `WSL`. **It is NOT recommended.**
  - Furthermore, using Windows ***WITHOUT*** Git Bash is ***REALLY NOT*** recommended as you might run into unusual problems.
- `JDK 11`
- `Git` (Linux/WSL) **or** `Git Bash` (Windows)
- Working GitHub SSH Cloning. You can find an in-depth tutorial [here](https://docs.github.com/en/github/authenticating-to-github/connecting-to-github-with-ssh).

### Procedure
After executing the following commands depending on which platform you're on, you should have a completely built and working JAR for SootReports-Waterfall. The newly built JAR file will be located in `build/libs/SootReports-Waterfall-1.1.jar`.

#### Linux/WSL/Git Bash
```bash
git clone git@github.com:sootdevs/SootReports-Waterfall.git
cd SootReports-Waterfall
./gradlew build shadowJar
```

#### Windows (without Git Bash)
```bash
git clone git@github.com:sootdevs/SootReports-Waterfall.git
cd SootReports-Waterfall
gradlew.bat build shadowJar
```

## Miscellaneous
### Plugin Configuration (config.yml)
```yaml
# SootReports-Waterfall v1.1 Configuration File

discord-webhook-reports: "https://discord.com/api/webhooks/xxxxxxxxxxxxxxx/xxxxxxxxxxxxxxx"
discord-webhook-bugs: "https://discord.com/api/webhooks/xxxxxxxxxxxxxxx/xxxxxxxxxxxxxxx"
discord-webhook-suggestions: "https://discord.com/api/webhooks/xxxxxxxxxxxxxxx/xxxxxxxxxxxxxxx"

reports-role-id: "123456789"
bugs-role-id: "123456789"
```

### Credits
- [CodeError](https://github.com/CodeTheDev) (Programmer of This Plugin)
- [k3kdude](https://github.com/k3kdude) (Base for [DiscordWebhook](https://gist.github.com/k3kdude/fba6f6b37594eae3d6f9475330733bdb) Class)
- [TydiumCraft](https://tydiumcraft.net/) ([Skin API Website](https://tydiumcraft.net/docs/skinapi))
