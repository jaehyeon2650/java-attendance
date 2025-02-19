package dto;

import java.util.Map;

public class AbsenceCrewDto implements Comparable<AbsenceCrewDto> {
    private final String username;
    private final Map<String,Integer> results;
    private final String classifyAbsenceLevel;

    public AbsenceCrewDto(String username, Map<String, Integer> results, String classifyAbsenceLevel) {
        this.username = username;
        this.results = results;
        this.classifyAbsenceLevel = classifyAbsenceLevel;
    }

    @Override
    public int compareTo(AbsenceCrewDto o) {
        int myAbsenceCount = results.getOrDefault("지각",0);
        myAbsenceCount+=results.getOrDefault("결석",0)*3;
        int otherAbsenceCount = o.results.getOrDefault("지각",0);
        otherAbsenceCount += o.results.getOrDefault("결석",0)*3;

        int result = Integer.compare(otherAbsenceCount,myAbsenceCount);

        if(result==0){
            return this.username.compareTo(o.username);
        }

        return result;
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Integer> getResults() {
        return results;
    }

    public String getClassifyAbsenceLevel() {
        return classifyAbsenceLevel;
    }
}
