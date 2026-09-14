import { useState } from "react";
import {
  Box,
  Paper,
  Typography,
  TextField,
  Button,
  MenuItem,
  Select,
  InputLabel,
  FormControl,
  Stack
} from "@mui/material";
import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import api from "../api/api";

const MAX_AMOUNT = 50000;

function AddTransactionPage({ categories, onSave, onCancel }) {

  const [description, setDescription] = useState("");
  const [amount, setAmount] = useState("");
  const [categoryId, setCategoryId] = useState("");
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  const isValid = description.trim() !== ""
    && Number(amount) > 0
    && Number(amount) <= MAX_AMOUNT
    && categoryId !== "";

  const handleSave = () => {
    if (!isValid) return;

    setSaving(true);
    setError("");

    api.post("/transactions", {
      description,
      amount: Number(amount),
      categoryId
    })
      .then(() => {
        onSave();
      })
      .catch((err) => {
        console.error("Error creating transaction:", err);
        setError("Failed to create transaction.");
      })
      .finally(() => setSaving(false));
  };

  return (
    <Box>
      <Button startIcon={<ArrowBackIcon />} onClick={onCancel} sx={{ mb: 2 }}>
        Back to Transactions
      </Button>

      <Paper variant="outlined" sx={{ p: 4, maxWidth: 520, borderRadius: 2.5 }}>

        <Typography variant="h5" gutterBottom>
          Add Transaction
        </Typography>
        <Typography variant="body2" color="text.secondary" sx={{ mb: 3 }}>
          Enter the transaction details below.
        </Typography>

        <Stack spacing={2.5}>

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
            <InputLabel id="add-category-label">Category</InputLabel>
            <Select
              labelId="add-category-label"
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

          {error && <Typography color="error">{error}</Typography>}

          <Stack direction="row" spacing={2} justifyContent="flex-end">
            <Button onClick={onCancel} disabled={saving}>Cancel</Button>
            <Button onClick={handleSave} variant="contained" disabled={!isValid || saving}>
              Save
            </Button>
          </Stack>

        </Stack>

      </Paper>
    </Box>
  );
}

export default AddTransactionPage;
