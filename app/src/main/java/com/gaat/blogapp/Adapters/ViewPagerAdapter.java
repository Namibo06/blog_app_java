package com.gaat.blogapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.gaat.blogapp.R;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater inflater;

    //Construtor,dá para observar que somente carrega a visibilidade e o nome da Class
    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    private int images[]={
            R.drawable.p1,
            R.drawable.p1,
            R.drawable.p1,
    };

    private String titles[]={
            "Learn",
            "Create",
            "Enjoy"
    };

    private String descs[]={
            "Lorem impsolum",
            "Lorem impsolum 2",
            "Lorem impsolum 3"
    };

    //Aqui retorna a quantidade de titulos
    @Override
    public int getCount(){
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view,@NonNull Object object){
        return view == (LinearLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position){
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.view_pager,container,false);

        //init views
        ImageView imageView=v.findViewById(R.id.imgViewPager);
        TextView txtTitle=v.findViewById(R.id.txtTileViewPager);
        TextView txtDesc=v.findViewById(R.id.txtDescViewPager);

        //setando conforme a posição
        imageView.setImageResource(images[position]);
        txtTitle.setText(titles[position]);
        txtDesc.setText(descs[position]);

        //adicionarndo view e a retornando
        container.addView(v);
        return v;
    }

    public void destroyItem(@NonNull ViewGroup container,int position,Object object){
        //utilizado para remover a view
        container.removeView((LinearLayout)object);
    }
}
