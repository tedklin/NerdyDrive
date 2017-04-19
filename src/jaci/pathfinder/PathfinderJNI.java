//package jaci.pathfinder;
//
//import jaci.pathfinder.EncoderFollower;
//import jaci.pathfinder.SwerveModifier;
//
//import java.io.*;
//
//public class PathfinderJNI {
//
//    static boolean libLoaded = false;
//    static File jniLib = null;
//
//    static {
//        if (!libLoaded) {
//            try {
//                String os = System.getProperty("os.name");
//                String resolvedName;
//
//                if (os.startsWith("Windows"))
//                    resolvedName = "/Win/" + System.getProperty("os.arch") + "/";
//                else if (os.toLowerCase().contains("mac"))
//                    resolvedName = "/Mac/" + System.getProperty("os.arch") + "/";
//                else
//                    resolvedName = "/Linux/" + System.getProperty("os.arch") + "/";
//
//                System.err.println("Platform: " + resolvedName);
//                loadLibrary(resolvedName, "pathfinderjava", "pathfinderJNI");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            libLoaded = true;
//        }
//    }
//
//    public static void loadLibrary(String resolved, String libname, String tempname) throws IOException {
//        String os = System.getProperty("os.name");
//
//        if (os.startsWith("Windows"))
//            resolved += libname + ".dll";
//        else if (os.startsWith("Mac"))
//            resolved += "lib" + libname + ".dylib";
//        else
//            resolved += "lib" + libname + ".so";
//
//        InputStream is = PathfinderJNI.class.getResourceAsStream(resolved);
//        if (is != null) {
//            // Copy to temp file so we can actually load it
//            if (os.startsWith("Windows"))
//                jniLib = File.createTempFile(tempname, ".dll");
//            else if (os.startsWith("Mac"))
//                jniLib = File.createTempFile(tempname, ".dylib");
//            else
//                jniLib = File.createTempFile(tempname, ".so");
//
//            jniLib.deleteOnExit();
//            OutputStream ous = new FileOutputStream(jniLib);
//
//            byte[] buffer = new byte[1024];
//            int readBytes;
//            try {
//                while ((readBytes = is.read(buffer)) != -1) {
//                    ous.write(buffer, 0, readBytes);
//                }
//            } finally {
//                ous.close();
//                is.close();
//            }
//            try {
//                System.load(jniLib.getAbsolutePath());
//            } catch (UnsatisfiedLinkError e) {
//                System.loadLibrary(libname);
//            }
//        } else {
//            System.loadLibrary(libname);
//        }
//    }
//
//    public static Trajectory generateTrajectory(Waypoint[] waypoints, Trajectory.Config c) {
//        return new Trajectory(generateTrajectory(waypoints, c.fit, c.sample_count, c.dt, c.max_velocity, c.max_acceleration, c.max_jerk));
//    }
//    public static native Trajectory.Segment[] generateTrajectory(Waypoint[] waypoints, Trajectory.FitMethod fit, int samples, double dt, double max_velocity, double max_acceleration, double max_jerk);
//
//    public static Trajectory[] modifyTrajectoryTank(Trajectory traj, double wheelbase_width) {
//        Trajectory.Segment[][] mod = modifyTrajectoryTank(traj.segments, wheelbase_width);
//        return new Trajectory[] { new Trajectory(mod[0]), new Trajectory(mod[1]) };
//    }
//    public static native Trajectory.Segment[][] modifyTrajectoryTank(Trajectory.Segment[] source, double wheelbase_width);
//
//    public static Trajectory[] modifyTrajectorySwerve(Trajectory traj, double wheelbase_width, double wheelbase_depth, SwerveModifier.Mode mode) {
//        Trajectory.Segment[][] mod = modifyTrajectorySwerve(traj.segments, wheelbase_width, wheelbase_depth, mode);
//        return new Trajectory[] { new Trajectory(mod[0]), new Trajectory(mod[1]), new Trajectory(mod[2]), new Trajectory(mod[3]) };
//    }
//    public static native Trajectory.Segment[][] modifyTrajectorySwerve(Trajectory.Segment[] source, double wheelbase_width, double wheelbase_depth, SwerveModifier.Mode mode);
//
//    public static native void trajectorySerialize(Trajectory.Segment[] source, String filename);
//    public static native Trajectory.Segment[] trajectoryDeserialize(String filename);
//
//    public static native void trajectorySerializeCSV(Trajectory.Segment[] source, String filename);
//    public static native Trajectory.Segment[] trajectoryDeserializeCSV(String filename);
//}
