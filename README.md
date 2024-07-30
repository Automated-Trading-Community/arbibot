# Arbitbot
The repo contains the open-source arbitrage bot **Arbibot**. The overall bot architecture is based on the [hexagonal architecture](https://alistair.cockburn.us/hexagonal-architecture/) and is implemented using Java 17.

This repo currently contains two subdirectories:
- [arbibot-core](arbibot-core/README.md) contains the core library of arbibot. It contains the entities, ports and use cases of the hexagonal architecture.
- [arbibot-infra](arbibot-infra/README.md) contains the bot infrastructure. Is contains the adapters which implements the ports of the input and output ports of the core library.

# Types of arbitrage supported
Arbibot currently supports the following types of arbitrage :
- Triangular arbitrage (🟠 ${\color{orange}\textsf{In coming}}$)

# Javadoc
The [Javadoc is available online](https://automated-trading-community.github.io/arbibot/). 
Other documents will be provided as the development of the project progresses.

# Run the project
You can run the project using Maven with the following command :

```
mvn exec:java
```

# Want to join the project?
If you want to help us by joining the project, feel free to contact one of the administrators of the repo:
- [SChoumiloff](https://github.com/SChoumiloff)
- [Sébastien GUILLEMIN](https://github.com/SebastienGuillemin)