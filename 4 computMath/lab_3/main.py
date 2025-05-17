import sys

def main():
    if len(sys.argv) > 1 and sys.argv[1] == "--console":
        from console_app import console_main
        console_main()
    else:
        from gui.app import IntegrationApp
        app = IntegrationApp()
        app.mainloop()

if __name__ == "__main__":
    main()
