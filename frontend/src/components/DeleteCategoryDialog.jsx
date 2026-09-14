import { useState } from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  Button
} from "@mui/material";
import api from "../api/api";

function DeleteCategoryDialog({ open, category, onClose, onDeleted }) {

  const [deleting, setDeleting] = useState(false);
  const [error, setError] = useState("");

  const handleDelete = () => {
    setDeleting(true);
    setError("");

    api.delete(`/categories/${category.id}`)
      .then(() => {
        onDeleted();
        onClose();
      })
      .catch((err) => {
        console.error("Error deleting category:", err);
        setError("Failed to delete category. It may still be used by existing transactions.");
      })
      .finally(() => setDeleting(false));
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Delete Category</DialogTitle>

      <DialogContent>
        <DialogContentText>
          Are you sure you want to delete
          {category ? ` "${category.name}"` : " this category"}?
          This action cannot be undone.
        </DialogContentText>

        {error && <p style={{ color: "red" }}>{error}</p>}
      </DialogContent>

      <DialogActions>
        <Button onClick={onClose} disabled={deleting}>Cancel</Button>
        <Button onClick={handleDelete} color="error" variant="contained" disabled={deleting}>
          Delete
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default DeleteCategoryDialog;
