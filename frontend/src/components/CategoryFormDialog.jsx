import { useEffect, useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Button,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
  Stack
} from "@mui/material";
import api from "../api/api";

const TYPES = ["INCOME", "EXPENSE"];

function CategoryFormDialog({ open, category, onClose, onSaved }) {

  const isEditMode = category != null;

  const [name, setName] = useState("");
  const [type, setType] = useState("");
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    if (open) {
      setName(category?.name ?? "");
      setType(category?.type ?? "");
      setError("");
    }
  }, [open, category]);

  const isValid = name.trim() !== "" && type !== "";

  const handleSave = () => {
    if (!isValid) return;

    setSaving(true);
    setError("");

    const request = isEditMode
      ? api.patch(`/categories/${category.id}`, { name, type })
      : api.post("/categories", { name, type });

    request
      .then(() => {
        onSaved();
        onClose();
      })
      .catch((err) => {
        console.error("Error saving category:", err);
        setError("Failed to save category.");
      })
      .finally(() => setSaving(false));
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="xs">
      <DialogTitle>{isEditMode ? "Edit Category" : "Add Category"}</DialogTitle>

      <DialogContent>
        <Stack spacing={2} sx={{ mt: 1 }}>

          <TextField
            label="Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            fullWidth
          />

          <FormControl fullWidth>
            <InputLabel id="category-type-label">Type</InputLabel>
            <Select
              labelId="category-type-label"
              label="Type"
              value={type}
              onChange={(e) => setType(e.target.value)}
            >
              {TYPES.map((t) => (
                <MenuItem key={t} value={t}>{t}</MenuItem>
              ))}
            </Select>
          </FormControl>

          {error && <span style={{ color: "red" }}>{error}</span>}

        </Stack>
      </DialogContent>

      <DialogActions>
        <Button onClick={onClose} disabled={saving}>Cancel</Button>
        <Button onClick={handleSave} variant="contained" disabled={!isValid || saving}>
          {isEditMode ? "Update" : "Save"}
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default CategoryFormDialog;
