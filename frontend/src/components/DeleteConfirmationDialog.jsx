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

function DeleteConfirmationDialog({ open, transaction, onClose, onDeleted }) {

  const [deleting, setDeleting] = useState(false);
  const [error, setError] = useState("");

  const handleDelete = () => {
    setDeleting(true);
    setError("");

    api.delete(`/transactions/${transaction.id}`)
      .then(() => {
        onDeleted();
        onClose();
      })
      .catch((err) => {
        console.error("Error deleting transaction:", err);
        setError("Failed to delete transaction.");
      })
      .finally(() => setDeleting(false));
  };

  return (
    <Dialog open={open} onClose={onClose}>
      <DialogTitle>Delete Transaction</DialogTitle>

      <DialogContent>
        <DialogContentText>
          Are you sure you want to delete
          {transaction ? ` "${transaction.description}" ($${transaction.amount})` : " this transaction"}?
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

export default DeleteConfirmationDialog;
