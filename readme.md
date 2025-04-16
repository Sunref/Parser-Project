# CPRL Project - Parser
### About
 This is a project assignment by <a href="https://github.com/davidbuzatto">David Buzatto</a> and made by <a href="https://github.com/Sunref">Sunref</a> and <a href="https://github.com/gm64x">gm64x</a> for the Compiler Construction class.

# CPRL Project

This project contains a compiler for the CPRL (Compiler Programming Language) language, originally developed using the Netbeans IDE and subsequently adapted for use with the Zed editor.

## Project Structure

```
├── examples              # Example programs and test cases
│   ├── Correct           # Examples with correct syntax and semantics
│   ├── Incorrect         # Examples with deliberate errors for testing
│   └── ScannerTests      # Tests specifically for the scanner component
├─── ParserAnalyzer        # Main compiler project directory
│   ├── build             # Compiled output and build artifacts
│   │   ├── classes       # Compiled Java class files
│   │   │   ├── edu
│   │   │   │   └── citadel
│   │   │   │       ├── compiler   # Compiled compiler framework classes
│   │   │   │       └── cprl       # Compiled CPRL language classes
│   │   │   └── test              # Compiled test classes
│   │   │       ├── compiler      # Compiler framework tests
│   │   │       └── cprl          # CPRL language tests
│   │   └── test             # Test build directory
│   │       └── classes      # Compiled test classes
│   │           └── test
│   │               └── cprl # CPRL test classes
│   ├── lib                  # External libraries
│   │   ├── CopyLibs         # NetBeans copy libraries
│   │   └── junit_4          # JUnit testing framework
│   ├── nbproject            # NetBeans project configuration
│   │   └── private          # User-specific NetBeans settings
│   ├── src                  # Source code directory
│   │   ├── edu              # Package hierarchy root
│   │   │   └── citadel      # Organization package
│   │   │       ├── compiler # Generic compiler components
│   │   │       └── cprl     # CPRL language implementation
│   │   └── test             # Test source code
│   │       ├── compiler     # Tests for compiler framework
│   │       └── cprl         # Tests for CPRL implementation
│   └── test                 # Additional test resources
│       └── test             # Test hierarchy
│           └── cprl         # CPRL-specific tests
└── README.md             # This file
```

## Zed Configuration

1.  Open the project folder in Zed.
2.  Java classpath, formatting, and execution task settings are already defined in the files within the `.zed/` directory.

## Running Tests

Use Zed's execution menu to run the tests configured in `launch.json`.

## Dependencies

-   JUnit 4.13.1
-   Hamcrest 1.3
