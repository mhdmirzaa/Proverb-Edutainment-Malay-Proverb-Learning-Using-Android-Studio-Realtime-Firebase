static class AnsweredProverbsAdapter extends RecyclerView.Adapter<AnsweredProverbsAdapter.ViewHolder> {

    private final List<String> answeredProverbs;
    private final Map<String, String> explanations;

    public AnsweredProverbsAdapter(List<String> answeredProverbs, Map<String, String> explanations) {
        this.answeredProverbs = answeredProverbs;
        this.explanations = explanations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_proverbs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String proverbText = answeredProverbs.get(position);
        String explanationText = explanations.get(proverbText);

        holder.proverbTextView.setText(proverbText);
        holder.explanationTextView.setText(explanationText != null ? explanationText : "Explanation unavailable");
    }

    @Override
    public int getItemCount() {
        return answeredProverbs.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView proverbTextView, explanationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            proverbTextView = itemView.findViewById(R.id.proverbTextView);
            explanationTextView = itemView.findViewById(R.id.explanationTextView);
        }
    }
}
