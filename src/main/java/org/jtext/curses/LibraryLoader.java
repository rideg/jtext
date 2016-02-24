package org.jtext.curses;

import java.io.File;

public final class LibraryLoader {

    private static boolean loaded = Boolean.parseBoolean(System.getProperty("test.mode", "false"));

    private LibraryLoader() {
    }

    public static synchronized void load() {
        if (!loaded) {
            final OS os = getOperatingSystem();
            final String arch = System.getProperty("sun.arch.data.model");
            final String path = System.getProperty("org.jtext.ldPath", "native");
            final String libPath =
                    path + "/" + os.name().toLowerCase() + "_" + arch + "/libcursesjni." + os.extension();
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

}
