# CPRL Project - Parser
### About
 This is a project assignment by <a href="https://github.com/davidbuzatto">David Buzatto</a> and made by <a href="https://github.com/Sunref">Sunref</a> and <a href="https://github.com/gm64x">gm64x</a> for the Compiler Construction class.

# CPRL Project

This project contains a compiler for the CPRL (Compiler Programming Language) language, originally developed using the Netbeans IDE and subsequently adapted for use with the Zed editor.

## Project Structure

```
├── examples              # Example programs and test cases
│   ├── Correct           # Examples with correct syntax and semantics
│   │   ├── Arrays        # Examples using array data structures
│   │   ├── ArraysAndProcedures  # Examples combining arrays with procedures
│   │   ├── CPRL0         # Basic CPRL language examples
│   │   └── Subprograms   # Examples using subprograms
│   ├── Incorrect         # Examples with deliberate errors for testing
│   │   ├── Arrays        # Array-related error examples
│   │   ├── CPRL0         # Basic CPRL error examples
│   │   └── Subprograms   # Subprogram error examples
│   └── ScannerTests      # Tests specifically for the scanner component
│       ├── Correct       # Valid scanner test cases
│       └── Incorrect     # Invalid scanner test cases
├─── ParserAnalyzer        # Main compiler project directory
│   ├── build             # Compiled output and build artifacts
│   │   ├── classes       # Compiled Java class files
│   │   │   ├── edu
│   │   │   │   └── citadel
│   │   │   │       ├── compiler   # Compiled compiler framework classes
│   │   │   │       │   └── util   # Compiler utilities
│   │   │   │       └── cprl       # Compiled CPRL language classes
│   │   │   └── test              # Compiled test classes
│   │   │       ├── compiler      # Compiler framework tests
│   │   │       └── cprl          # CPRL language tests
│   │   ├── empty            # Directory for temporary build files
│   │   ├── generated-sources # Auto-generated source code
│   │   │   └── ap-source-output  # Annotation processor output
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
│   │   │       │   └── util # Utility classes for compilation
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
