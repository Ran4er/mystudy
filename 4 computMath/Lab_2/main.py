import sys
from modules.utils import decimal_from_str, validate_interval
from interfaces.cli_interface import CLIInterface
from interfaces.graphical_interface import MathApp

def main():
    if len(sys.argv) > 1 and sys.argv[1] == '--cli':
        cli = CLIInterface()
        cli.run()
    else:
        app = MathApp()
        app.mainloop()

if __name__ == "__main__":
    main()