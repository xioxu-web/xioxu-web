package com.example.data.structure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoxu123
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出和为目标值 target 的那两个整数，并返回它们的数组下标
 */
public class Solution {

    public static void main(String[] args) {
        int[] nums={2,7,11,15,18,20,24};
        int target=39;
        int[] index = twoSum(nums,target );
        System.out.println(Arrays.toString(index));

    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> hashtable = new HashMap<>();
        for (int i = 0; i < nums.length; ++i) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{
                        hashtable.get(target - nums[i]), i
                };
            }
            hashtable.put(nums[i], i);
        }
        return new int[0];
    }

}
