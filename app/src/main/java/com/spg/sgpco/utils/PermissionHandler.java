package com.spg.sgpco.utils;


/**
 * Created by R.taghizadeh on 10/17/2017.
 */

public class PermissionHandler {
//
//    public static Activity CurrentActivity = null;
//    private BroadcastReceiver permissionReceiver;
//    private OnPermissionResponse listener;
//
//    /**
//     *  One Permission
//     */
//    private void checkPermission(String permission, OnPermissionResponse _listener) {
//        listener = _listener;
//
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (CurrentActivity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
//                listener.onPermissionGranted();
//            } else {
//                registerReceiver();
//                ActivityCompat.requestPermissions(CurrentActivity, new String[]{permission}, 1);
//            }
//
//        } else {
//            listener.onPermissionGranted();
//        }
//    }
//    public static boolean ReadAndWriteSdCardPermission(){
//        if (checkWriteExternalPermission() && checkReadExternalPermission()) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    private static boolean checkWriteExternalPermission() {
//
//        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
//        int res = CurrentActivity.checkCallingOrSelfPermission(permission);
//        return (res == PackageManager.PERMISSION_GRANTED);
//    }
//
//    private static boolean checkReadExternalPermission() {
//
//        String permission = "android.permission.READ_EXTERNAL_STORAGE";
//        int res = CurrentActivity.checkCallingOrSelfPermission(permission);
//        return (res == PackageManager.PERMISSION_GRANTED);
//    }
//    private void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
//            listener.onPermissionGranted();
//        }else {
//            listener.onPermissionDenied();
//        }
//    }
//
//    public interface OnPermissionResponse{
//        void onPermissionGranted();
//        void onPermissionDenied();
//    }
//
//    private void registerReceiver() {
//        permissionReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Bundle extras = intent.getExtras();
//                if (extras != null)
//                {
//                    int requestCode = extras.getInt("requestCode");
//                    String[] permissions = intent.getStringArrayExtra("permissions");
//                    int[] grantResults = intent.getIntArrayExtra("grantResults");
//                    onRequestPermissionsResult(requestCode,permissions,grantResults);
//                    CurrentActivity.unregisterReceiver(permissionReceiver);
//                }
//            }
//        };
//
//        IntentFilter localIntentFilter = new IntentFilter();
//        localIntentFilter.addAction("PERMISSION_RECEIVER");
//        CurrentActivity.registerReceiver(permissionReceiver, localIntentFilter);
//    }
//
//
//    public static void checkNetwork( final OnNetworkResponse networkInterface) {
//        new PermissionHandler().checkPermission( Manifest.permission.ACCESS_NETWORK_STATE, new OnPermissionResponse() {
//            @Override
//            public void onPermissionGranted() {
//                ConnectivityManager cm =
//                        (ConnectivityManager) CurrentActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//                boolean isConnected = activeNetwork != null &&
//                        activeNetwork.isConnectedOrConnecting();
//                networkInterface.onConnectionState(isConnected);
//
//            }
//
//            @Override
//            public void onPermissionDenied() {
//                networkInterface.onConnectionState(true);
//            }
//        });
//    }
}