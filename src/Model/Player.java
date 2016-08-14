package Model;


import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {
        private static final long serialVersionUID = 1L;

        private String mFirstName;
        private String mLastName;
        private int mHeightInInches;
        private boolean mPreviousExperience;

        public Player(String firstName, String lastName, int heightInInches, boolean previousExperience) {
            this.mFirstName = firstName;
            this.mLastName = lastName;
            this.mHeightInInches = heightInInches;
            this.mPreviousExperience = previousExperience;
        }

        public String getFirstName() {
            return mFirstName;
        }

        public String getLastName() {
            return mLastName;
        }

        public int getHeightInInches() {
            return mHeightInInches;
        }

        public boolean isPreviousExperience() {
            return mPreviousExperience;
        }

        @Override
        public int compareTo(Player p) {
            if(mLastName.equals(p.getLastName())){
                return getFirstName().compareTo(p.getFirstName());
            }
            else{
                return getLastName().compareTo(p.getLastName());
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Player)) return false;

            Player player = (Player) o;

            if (mHeightInInches != player.mHeightInInches) return false;
            if (mPreviousExperience != player.mPreviousExperience) return false;
            if (!mFirstName.equals(player.mFirstName)) return false;
            return mLastName.equals(player.mLastName);

        }

        @Override
        public int hashCode() {
            int result = mFirstName.hashCode();
            result = 31 * result + mLastName.hashCode();
            result = 31 * result + mHeightInInches;
            result = 31 * result + (mPreviousExperience ? 1 : 0);
            return result;
        }

        @Override
        public String toString(){
            String experience = "";

            if(mPreviousExperience){
                experience = "Yes";
            }else{
                experience = "No";
            }

            return String.format("%s , %s Height: %d Experience: %s",
                    mLastName, mFirstName, mHeightInInches, experience);
        }//end toString method

    }

