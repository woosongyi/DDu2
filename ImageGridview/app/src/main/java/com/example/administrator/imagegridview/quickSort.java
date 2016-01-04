package com.example.administrator.imagegridview;

import com.example.administrator.cameragps.Picture;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016-01-04.
 */
public class quickSort {

    public void Qsort(ArrayList<Picture> pictureArrayList, int left, int right){

        int pivot;
        int i, last;

        if(left>=right){return;}

        pivot=(left+right)/2;

        swap(pictureArrayList,left,pivot);

        last = left;

        for(i = left +1; i <= right; i ++){
            Date d1 = pictureArrayList.get(i).getToday();
            Date d2 = pictureArrayList.get(left).getToday();
            //날짜비교
            if(d1.after(d2)){ //d1 > d2
                swap(pictureArrayList,++last,i);
            }
        }

        swap(pictureArrayList,left,last);
        Qsort(pictureArrayList,left,last-1);
        Qsort(pictureArrayList,left+1,right);
    }
    void swap(ArrayList<Picture> pictureArrayList,int i, int j){
        Picture temp;

        temp = pictureArrayList.get(i);
        pictureArrayList.set(i,pictureArrayList.get(j));
        pictureArrayList.set(j,temp);

    }

}
