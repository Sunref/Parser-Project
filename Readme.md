# CPRL Project - Parser

![Made with](https://img.shields.io/badge/Made%20with-Java-EC4134?labelColor=black&style=flat-square&logo=openjdk)
![Made with](https://img.shields.io/badge/Made%20with-Netbeans-0751CF?labelColor=black&style=flat-square&logo=apachenetbeanside)
![Made with](https://img.shields.io/badge/Made%20with-ZED-grey?labelColor=black&style=flat&logo=zedindustries)

*[Leia em Português](readme-ptbr.md)*

## About

This is a project assignment by <a href="https://github.com/davidbuzatto">David Buzatto</a> and made by <a href="https://github.com/Sunref">Sunref</a> and <a href="https://github.com/gm64x">gm64x</a> for the Compiler Construction class.

> [!NOTE]  
> This repository contains an implementation of a compiler for the CPRL (Compiler PRogramming Language).




## Project Structure

```
.
├── examples               # Example CPRL programs for testing
│   ├── Correct           # Correct CPRL programs
│   │   ├── Arrays
│   │   ├── ArraysAndProcedures
│   │   ├── CPRL0
│   │   └── Subprograms
│   ├── Incorrect        # Incorrect CPRL programs for error testing
│   └── ScannerTests     # Tests specifically for the scanner
├── ParserAnalyzer    # Main project source code
    ├── lib              # Library dependencies
    ├── src              # Source code
        ├── edu
            ├── citadel
                ├── compiler
                └── cprl   # CPRL compiler implementation
```

> [!TIP]  
> To run this project in code editors or the Netbeans IDE, you need to open the workbench in the `./ParserAnalyzer` directory. If your code editor supports `tasks.json` or `launch.json`, this repository is already prepared for this.

## Features

- Implementation of First and Follow sets for CPRL grammar
- Scanner for lexical analysis
- Parser for syntactic analysis
- Symbol table management
- Comprehensive test suite with correct and incorrect CPRL programs

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Ant (for building)

### Building the Project

```bash
cd ParserAnalyzer
ant build
```

### Running Tests

```bash
ant test
```

## Key Components

- **Scanner**: Performs lexical analysis, converting source code into tokens
- **Parser**: Analyzes the syntactic structure of the program
- **FirstFollowSets**: Implements the calculation of First and Follow sets for CPRL grammar
- **Symbol Table**: Manages identifier declarations and their scopes

## License

This project is available under the MIT License - see the LICENSE file for details.
