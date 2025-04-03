# CPRL Project - Parser
### About
 This is a project assignment by <a href="https://github.com/davidbuzatto">David Buzatto</a> and made by <a href="https://github.com/Sunref">Sunref</a> and <a href="https://github.com/gm64x">gm64x</a> for the Compiler Construction class.

# CPRL Project

This project contains a compiler for the CPRL (Compiler Programming Language) language, originally developed using the Netbeans IDE and subsequently adapted for use with the Zed editor.

## Project Structure

```
├── src                   # Source code
│   ├── edu/citadel       # Main packages
│   │   ├── compiler      # Compiler components
│   │   └── cprl          # CPRL-specific implementation
│   └── Gramática CPRL.txt # CPRL Grammar documentation
├── test                  # Compiler tests
├── lib                   # External libraries
│   ├── CopyLibs          # Netbeans libraries
│   └── junit_4           # JUnit for testing
├── .zed                  # Zed configurations
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
