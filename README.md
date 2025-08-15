# Power Calculator - X^Y Calculator

A Java-based mathematical power calculator with a graphical user interface that computes x raised to the power of y (x^y) with comprehensive error handling and accessibility features.

## Features

- **Mathematical Precision**: Implements a custom power function that handles edge cases and special mathematical scenarios
- **Graphical User Interface**: Modern Swing-based GUI with intuitive design
- **Accessibility Support**: Full Java Accessibility API integration for screen readers and assistive technologies
- **Error Handling**: Comprehensive error messages for invalid inputs and mathematical edge cases
- **Unit Testing**: Extensive JUnit 5 test suite covering all mathematical scenarios
- **Code Quality**: Follows established Java programming standards with Checkstyle, PMD, and SpotBugs integration

## Mathematical Capabilities

The calculator handles various mathematical scenarios:

- **Basic Operations**: Integer and fractional powers
- **Special Values**: Zero, one, negative one, infinity, and NaN
- **Edge Cases**: Negative bases with integer/fractional exponents
- **Complex Numbers**: Proper handling of cases that result in complex numbers
- **Overflow/Underflow**: Detection and handling of numerical overflow

## Installation and Usage

### Prerequisites

- Java 11 or higher
- Gradle 7.0 or higher

### Building the Project

```bash
# Clone the repository
git clone <repository-url>
cd sep_final

# Build the project
./gradlew build

# Run the application
./gradlew run
```

### Running Tests

```bash
# Run all tests
./gradlew test

# Run with detailed output
./gradlew test --info
```

### Code Quality Checks

```bash
# Run Checkstyle
./gradlew checkstyleMain

# Run PMD
./gradlew pmdMain

# Run all quality checks
./gradlew check
```

## Project Structure

```
sep_final/
├── src/
│   ├── main/java/org/example/
│   │    ├──Main.java    # Main application class
│   │    ├──PowerCalculator.java # Power calculation logic
│   │    └──PowerCalculatorGUI.java # GUI implementation
│   └── test/java/org/example/
│       └── PowerCalculatorTest.java # Unit tests
├── config/
│   └── checkstyle/
│       └── checkstyle.xml          # Checkstyle configuration
├── build.gradle.kts                # Build configuration
└── README.md                       # This file
```

## Usage Examples

### Basic Calculations

- `2^3 = 8`
- `4^0.5 = 2` (square root)
- `8^(1/3) = 2` (cube root)
- `2^(-1) = 0.5`

### Special Cases

- `0^0 = 1` (by definition)
- `1^x = 1` (for any x)
- `(-1)^2 = 1`, `(-1)^3 = -1`
- `0^(-1) = Undefined` (division by zero)

## Technical Implementation

### Mathematical Algorithm

The power function implements a sophisticated algorithm that:

1. Handles special cases (0, 1, -1, infinity, NaN)
2. Manages negative bases with integer exponents
3. Detects complex number scenarios
4. Uses high-precision floating-point arithmetic
5. Implements proper overflow/underflow detection

### GUI Design Principles

- **Consistency**: Uniform layout and styling
- **Accessibility**: Full JAAPI support
- **Error Feedback**: Clear, helpful error messages
- **Responsive Design**: Adapts to different screen sizes
- **User-Friendly**: Intuitive input fields and clear results

### Code Quality Standards

- **Checkstyle**: Google Java Style Guide compliance
- **PMD**: Static code analysis for potential issues
- **SpotBugs**: Bytecode analysis for bug detection
- **JUnit 5**: Comprehensive unit testing
- **Semantic Versioning**: Proper version management

## Testing Strategy

The test suite covers:

- **Basic Operations**: Standard power calculations
- **Edge Cases**: Zero, infinity, NaN values
- **Special Values**: Negative bases, fractional exponents
- **Mathematical Properties**: Power laws and identities
- **GUI Components**: Interface element validation
- **Error Conditions**: Invalid inputs and mathematical exceptions

## Accessibility Features

- **Screen Reader Support**: Proper accessible names and descriptions
- **Keyboard Navigation**: Full keyboard accessibility
- **High Contrast**: Compatible with high contrast themes
- **Font Scaling**: Supports system font scaling
- **Focus Management**: Clear focus indicators

## Version History

- **1.0.0**: Initial release with full functionality
  - Complete power calculation implementation
  - GUI with accessibility features
  - Comprehensive test suite
  - Code quality tools integration

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all quality checks pass
6. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Author

Kapil Soni - kapssoni333@gmail.com

## Acknowledgments

- Java Swing for the GUI framework
- JUnit 5 for testing framework
- Checkstyle and PMD for code quality
- Java Accessibility API for accessibility features

## Repository Information

- **Hosting Service**: GitHub
- **Repository URL**: [SOEN_6011_final_project](https://github.com/kapildev333/SOEN_6011_final_project)
- **Commit Quality**: High-quality commit messages following conventional commits
- **Documentation**: Comprehensive README and inline code documentation 
