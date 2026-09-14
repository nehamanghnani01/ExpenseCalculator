import { createTheme } from "@mui/material/styles";

const theme = createTheme({
  palette: {
    mode: "light",
    primary: {
      main: "#1e3a5f",
      light: "#3c5a80",
      dark: "#122539"
    },
    secondary: {
      main: "#2dd4bf"
    },
    background: {
      default: "#f4f6f9",
      paper: "#ffffff"
    },
    text: {
      primary: "#1a1d23",
      secondary: "#5f6672"
    }
  },
  shape: {
    borderRadius: 10
  },
  typography: {
    fontFamily: '"Segoe UI", system-ui, Roboto, sans-serif',
    h4: {
      fontWeight: 600,
      letterSpacing: "-0.5px"
    },
    h6: {
      fontWeight: 600
    }
  },
  components: {
    MuiAppBar: {
      styleOverrides: {
        root: {
          boxShadow: "0 1px 2px rgba(0, 0, 0, 0.08)"
        }
      }
    },
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: "none",
          fontWeight: 600
        }
      }
    },
    MuiPaper: {
      styleOverrides: {
        root: {
          backgroundImage: "none"
        }
      }
    }
  }
});

export default theme;
