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

function EditTransactionDialog({ open, transaction, categories, onClose, onUpdated }) {

  const [description, setDescription] = useState("");
  const [amount, setAmount] = useState("");
  const [categoryId, setCategoryId] = useState("");
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    if (transaction) {
      setDescription(transaction.description ?? "");
      setAmount(transaction.amount ?? "");
      setCategoryId(transaction.category?.id ?? "");
      setError("");
    }
  }, [transaction]);

  const isValid = description.trim() !== "" && Number(amount) > 0 && categoryId !== "";

  const handleUpdate = () => {
    if (!isValid) return;

    setSaving(true);
    setError("");

    api.patch(`/transactions/${transaction.id}`, {
      description,
      amount: Number(amount),
      categoryId
    })
      .then(() => {
        onUpdated();
        onClose();
      })
      .catch((err) => {
        console.error("Error updating transaction:", err);
        setError("Failed to update transaction.");
      })
      .finally(() => setSaving(false));
  };

  return (
    <Dialog open={open} onClose={onClose} fullWidth maxWidth="sm">
      <DialogTitle>Edit Transaction</DialogTitle>

      <DialogContent>
        <Stack spacing={2} sx={{ mt: 1 }}>

          <TextField
            label="Description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            fullWidth
          />

          <TextField
            label="Amount"
            type="number"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            fullWidth
          />

          <FormControl fullWidth>
            <InputLabel id="edit-category-label">Category</InputLabel>
            <Select
              labelId="edit-category-label"
              label="Category"
              value={categoryId}
              onChange={(e) => setCategoryId(e.target.value)}
            >
              {categories.map((category) => (
                <MenuItem key={category.id} value={category.id}>
                  {category.name} ({category.type})
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          {error && <span style={{ color: "red" }}>{error}</span>}

        </Stack>
      </DialogContent>

      <DialogActions>
        <Button onClick={onClose} disabled={saving}>Cancel</Button>
        <Button onClick={handleUpdate} variant="contained" disabled={!isValid || saving}>
          Update
        </Button>
      </DialogActions>
    </Dialog>
  );
}

export default EditTransactionDialog;
