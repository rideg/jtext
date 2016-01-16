package org.jtext.system;

import java.io.File;

public class LibraryLoader {

    private enum OS {
        MAC("dylib"),
        LINUX("so"),
        BSD("so"),
        WINDOWS("dll");

        private final String extension;

        OS(final String extension) {
            this.extension = extension;
        }

        public String extension() {
            return extension;
        }
    }

    private static boolean loaded = false;


    public static synchronized void load() {
        if (!loaded) {
            final OS os = getOperatingSystem();
            final String arch = System.getProperty("sun.arch.data.model");
            final String libPath = "native/" + os.name().toLowerCase() + "_" + arch + "/libcurses." + os.extension();
            System.load(new File(libPath).getAbsolutePath());
            loaded = true;
        }
    }

    private static OS getOperatingSystem() {
        String osName = System.getProperty("os.name");
        for (OS os : OS.values()) {
            if (osName.toUpperCase().startsWith(os.name())) {
                return os;
            }
        }
        throw new IllegalStateException("Cannot determine operating system! " + osName);
    }

}
