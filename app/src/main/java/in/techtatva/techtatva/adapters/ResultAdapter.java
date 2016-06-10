package in.techtatva.techtatva.adapters;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import in.techtatva.techtatva.R;
import in.techtatva.techtatva.fragments.ResultDetailsFragment;
import in.techtatva.techtatva.models.ResultModel;

/**
 * Created by Naman on 6/9/2016.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>{

    private List<ResultModel> results;
    private FragmentManager fm;

    public ResultAdapter( FragmentManager fm, List<ResultModel> results){
        this.results=results;
        this.fm=fm;
    }

    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.result_item, parent, false);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ResultAdapter.ViewHolder holder, int position) {
        ResultModel result = results.get(position);
        holder.resultName.setText(result.getResultName());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView resultLogo;
        TextView resultName;

        public ViewHolder(View itemView) {
            super(itemView);

            resultLogo=(ImageView)itemView.findViewById(R.id.result_logo_image_view);
            resultName=(TextView)itemView.findViewById(R.id.result_name_text_view);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(view.getId()==itemView.getId()){
                DialogFragment fragment= ResultDetailsFragment.newInstance();
                fragment.setStyle( DialogFragment.STYLE_NO_TITLE, 0);
                fragment.show(fm,"fragment_result_details");
            }
        }
    }
}
