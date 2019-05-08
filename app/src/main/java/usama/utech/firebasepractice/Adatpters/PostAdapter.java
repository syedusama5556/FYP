package usama.utech.firebasepractice.Adatpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import usama.utech.firebasepractice.ModelClasses.PostDriver;
import usama.utech.firebasepractice.ModelClasses.PostRider;
import usama.utech.firebasepractice.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    private ArrayList<PostDriver> list = new ArrayList<>();
    private ArrayList<PostRider> listrider = new ArrayList<>();
    boolean isDriverP = true;

    Context context;

    public PostAdapter( Context context,ArrayList<PostDriver> list, ArrayList<PostRider> listrider, boolean isDriverP) {
        this.list = list;
        this.listrider = listrider;
        this.isDriverP = isDriverP;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_row_rec_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        System.err.println("working "+ position);

        if (isDriverP) {

            PostDriver obj = list.get(position);

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context, "Clicked " + position, Toast.LENGTH_SHORT).show();

                }
            });

            holder.nametxt.setText(obj.getFullname());
            holder.startpointtxt.setText(obj.getStartpoint());
            holder.startpointtxt.setSelected(true);

            holder.endpointtxt.setText(obj.getEndpoint());
            holder.endpointtxt.setSelected(true);


            Picasso.get()
                    .load(obj.getProfileimgurl())
                    .placeholder(R.drawable.placeholder_user)
                    .error(R.drawable.ic_close)
                    .into(holder.user_profile_img_rec_post);

        }else{

            PostRider obj = listrider.get(position);

            holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context, "Clicked " + position, Toast.LENGTH_SHORT).show();

                }
            });

            holder.nametxt.setText(obj.getFullname());
            holder.startpointtxt.setText(obj.getStartpoint());
            holder.startpointtxt.setSelected(true);

            holder.endpointtxt.setText(obj.getEndpoint());
            holder.endpointtxt.setSelected(true);


            Picasso.get()
                    .load(obj.getProfileimgurl())
                    .placeholder(R.drawable.placeholder_user)
                    .error(R.drawable.ic_close)
                    .into(holder.user_profile_img_rec_post);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CardView mainLayout;
        private TextView startpointtxt;
        private TextView endpointtxt;
        private TextView nametxt;
        private CircularImageView user_profile_img_rec_post;

        public MyViewHolder(View view) {
            super(view);
            startpointtxt = (TextView) view.findViewById(R.id.startpointtxt);
            endpointtxt = (TextView) view.findViewById(R.id.endpointtxt);
            nametxt = (TextView) view.findViewById(R.id.name_rec_post);
            mainLayout = view.findViewById(R.id.main_layout);
            user_profile_img_rec_post = (CircularImageView) view.findViewById(R.id.user_profile_img_rec_post);
        }
    }
}
