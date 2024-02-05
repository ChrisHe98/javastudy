package capers;

import java.io.File;
import java.io.IOException;

import static capers.Utils.*;

/** A repository for Capers 
 * @author Junhui Xu
 * The structure of a Capers Repository is as follows:
 *
 * .capers/ -- top level folder for all persistent data in your lab12 folder
 *    - dogs/ -- folder containing all of the persistent data for dogs
 *    - story -- file containing the current story
 *
 * TODO: change the above structure if you do something different.
 */
public class CapersRepository {
    /** Current Working Directory. */
    static final File CWD = new File(System.getProperty("user.dir"));

    /** Main metadata folder. */
    static final File CAPERS_FOLDER = Utils.join(".capers");

    /**
     * Does required filesystem operations to allow for persistence.
     * (creates any necessary folders or files)
     * Remember: recommended structure (you do not have to follow):
     *
     * .capers/ -- top level folder for all persistent data in your lab12 folder
     *    - dogs/ -- folder containing all of the persistent data for dogs
     *    - story -- file containing the current story
     */
    public static void setupPersistence() {
        if (!CAPERS_FOLDER.exists()) {
            boolean isCapersFolderCreated = CAPERS_FOLDER.mkdir();
            if (!isCapersFolderCreated) {
                // Handle the case where the .capers directory could not be created.
                System.err.println("Could not create the .capers directory.");
                return; // Stop execution or handle the error as appropriate.
            }
        }

        if (!Dog.DOG_FOLDER.exists()) {
            boolean isDogsFolderCreated = Dog.DOG_FOLDER.mkdir();
            if (!isDogsFolderCreated) {
                // Handle the case where the dogs directory could not be created.
                System.err.println("Could not create the dogs directory within .capers.");
                return; // Stop execution or handle the error as appropriate.
            }
        }

        final File story = Utils.join(".capers", "story");
        try {
            if (!story.exists()) {
                story.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create the story file.", e);
        }
    }

    /**
     * Appends the first non-command argument in args
     * to a file called `story` in the .capers directory.
     * @param text String of the text to be appended to the story
     */
    public static void writeStory(String text) {
        File story = Utils.join(".capers", "story");
        String existingContent = readContentsAsString(story);
        String newContent = existingContent + text + "\n";
        writeContents(story, newContent);
        System.out.println(newContent);
    }

    /**
     * Creates and persistently saves a dog using the first
     * three non-command arguments of args (name, breed, age).
     * Also prints out the dog's information using toString().
     */
    public static void makeDog(String name, String breed, int age) {
        Dog m = new Dog(name, breed, age);
        m.saveDog();
        System.out.println(m.toString());
    }

    /**
     * Advances a dog's age persistently and prints out a celebratory message.
     * Also prints out the dog's information using toString().
     * Chooses dog to advance based on the first non-command argument of args.
     * @param name String name of the Dog whose birthday we're celebrating.
     */
    public static void celebrateBirthday(String name) {
        Dog m = Dog.fromFile(name);
        m.haveBirthday();
        m.saveDog();
    }
}
