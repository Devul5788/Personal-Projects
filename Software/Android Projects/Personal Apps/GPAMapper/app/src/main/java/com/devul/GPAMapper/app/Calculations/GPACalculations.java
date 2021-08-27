package com.devul.GPAMapper.app.Calculations;

import android.content.Context;
import android.content.SharedPreferences;

import com.devul.GPAMapper.app.Assignments.Assignments;
import com.devul.GPAMapper.app.ConversionTables.Conversions;
import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.Semesters.Semesters;
import com.devul.GPAMapper.app.Subjects.Subjects;
import com.devul.GPAMapper.app.Years.Years;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class GPACalculations {
    private List<Assignments> assignments;
    private List<Conversions> conversions;
    private SharedPreferences prefs;

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(0.0);

        if (value > 0) {
            bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
        }

        return bd.doubleValue();
    }

    private double SumOfGradeRecieved(double sum, double PercentWeightage) {
        double percentD = PercentWeightage / 100;
        return sum * percentD;
    }

    private int percentWeightageCountForPercent(Subjects s, double PercentWeightage, DatabaseHandler db) {
        List<SubjectPercentCount> percentCounts = db.getAllAssignmentsByPercent(s.getID());

        int num = 0;

        for (SubjectPercentCount pc : percentCounts) {
            if (pc.getPercent() == PercentWeightage) {
                num = pc.getCount();
            }
        }

        return num;
    }

    public double getAverageSubjectGrade(Subjects s, DatabaseHandler db, Context c) {
        double sum = 0.0;
        double averageGrade;
        double numberOfPercentWeightages = (double) getSumOfPercentsForSubject(s.getID(), db) / 100.0;

        List<ScoresForPercents> sp = db.getScoreForPercent(s.getID());

        for (ScoresForPercents scp : sp) {
            double sumR = SumOfGradeRecieved(scp.getSum(), scp.getPercent());
            double count = (double) percentWeightageCountForPercent(s, scp.getPercent(), db);
            double weightedScore = sumR / count;
            sum = sum + weightedScore;
        }

        prefs = c.getSharedPreferences("prefs", MODE_PRIVATE);
        averageGrade = sum / numberOfPercentWeightages;
        return round(averageGrade, prefs.getInt("decimalPlace", 0));
    }

    public double getAverageSemesterGrade(int semesterID, List<Subjects> subs, DatabaseHandler db, Context c) {
        double sum = 0.0;
        double averageGrade = 0.0;
        double averageSemesterGradeP;

        for (Subjects sb : subs) {
            if (sb.getSemesterID() == semesterID && sb.getGradeP() > 0) {
                averageGrade += getAverageSubjectGrade(sb, db, c);
                sum++;
            }
        }

        if (sum > 0) {
            averageSemesterGradeP = averageGrade / sum;
        } else {
            return 0.0;
        }
        prefs = c.getSharedPreferences("prefs", MODE_PRIVATE);
        return round(averageSemesterGradeP, prefs.getInt("decimalPlace", 0));
    }

    public double getAverageYearGrade(int yearID, List<Semesters> sms, DatabaseHandler db, Context c) {
        double sum = 0.0;
        double averageGrade = 0.0;
        double averageYearGradeP;

        List<Subjects> subs = db.getAllSubjects(false, "", "");

        for (Semesters sm : sms) {
            if (sm.getYearID() == yearID && sm.getPercent() > 0) {
                averageGrade += getAverageSemesterGrade(sm.getID(), subs, db, c);
                sum++;
            }
        }

        if (sum > 0) {
            averageYearGradeP = averageGrade / sum;
        } else {
            averageYearGradeP = 0.0;
        }

        prefs = c.getSharedPreferences("prefs", MODE_PRIVATE);
        return round(averageYearGradeP, prefs.getInt("decimalPlace", 0));
    }

    public double getAverageHSGrade(List<Years> yrs, DatabaseHandler db, Context c) {
        double sum = 0.0;
        double averageGrade = 0.0;
        double averageHSGradeP;

        List<Semesters> subs = db.getAllSemesters(false, "", "");
        for (Years y : yrs) {
            if (y.getPercent() > 0) {
                averageGrade += getAverageYearGrade(y.getID(), subs, db, c);
                sum++;
            }
        }

        if (sum > 0) {
            averageHSGradeP = averageGrade / sum;
        } else {
            averageHSGradeP = 0.0;
        }
        prefs = c.getSharedPreferences("prefs", MODE_PRIVATE);
        return round(averageHSGradeP, prefs.getInt("decimalPlace", 0));
    }

    public int getAverageGradeLG(double AverageGrade, DatabaseHandler db, Context con) {
        int averageGradeLG = 0;
        prefs = con.getSharedPreferences("prefs", MODE_PRIVATE);

        conversions = db.getAllConversions();
        List<Conversions> cons = new ArrayList<>();
        if (prefs.getString("gpaTable", null) != null) {

            if (Objects.requireNonNull(prefs.getString("gpaTable", null)).equals("4.0")) {
                for (Conversions c : conversions) {
                    if (c.getConversionTableName().equals("4.0")) {
                        cons.add(c);
                    }
                }
            } else if (Objects.requireNonNull(prefs.getString("gpaTable", null)).equals("Custom")) {
                for (Conversions c : conversions) {
                    if (c.getConversionTableName().equals("Custom")) {
                        cons.add(c);
                    }
                }
            }

            List<ConversionNumbers> conNums = getConversionNumbers(cons);

            for (ConversionNumbers c : conNums) {
                if (c.getUpperBound() == 100) {
                    if (((AverageGrade > c.getLowerBound()) && (AverageGrade <= c.getUpperBound()))) {
                        String scoreRange = c.getLowerBound() + " - " + c.getUpperBound();
                        for (Conversions cns : cons) {
                            if (cns.getPercentage().equals(scoreRange)) {
                                averageGradeLG = cns.getLetterGrade();
                                break;
                            }
                        }
                        break;
                    }
                } else if (((AverageGrade >= c.getLowerBound()) && (AverageGrade < c.getUpperBound()))) {
                    String scoreRange = c.getLowerBound() + " - " + c.getUpperBound();
                    for (Conversions cns : cons) {
                        if (cns.getPercentage().equals(scoreRange)) {
                            averageGradeLG = cns.getLetterGrade();
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return averageGradeLG;
    }

    public double getAverageGPA(int averageGradeLG, DatabaseHandler db, Context con) {
        double averageGPA = 0.0;
        prefs = con.getSharedPreferences("prefs", MODE_PRIVATE);

        conversions = db.getAllConversions();
        List<Conversions> cons = new ArrayList<>();
        if (Objects.requireNonNull(prefs.getString("gpaTable", null)).equals("4.0")) {
            for (Conversions c : conversions) {
                if (c.getConversionTableName().equals("4.0")) {
                    cons.add(c);
                }
            }
        } else if (Objects.requireNonNull(prefs.getString("gpaTable", null)).equals("Custom")) {
            for (Conversions c : conversions) {
                if (c.getConversionTableName().equals("Custom")) {
                    cons.add(c);
                }
            }
        }

        for (Conversions c : cons) {
            if (c.getLetterGrade() == averageGradeLG) {
                averageGPA = c.getGPA();
                break;
            }
        }
        return averageGPA;
    }

    private List<ConversionNumbers> getConversionNumbers(List<Conversions> conversions) {
        List<ConversionNumbers> conversionNumbers = new ArrayList<>();

        for (Conversions c : conversions) {
            String percentage = c.getPercentage();
            int f = percentage.indexOf(" -");
            int s = percentage.lastIndexOf(" ") + 1;

            double firstNumber;
            double secondNumber;
            if (f != -1 && s != -1) {
                firstNumber = Double.parseDouble((percentage.substring(0, f)));
                secondNumber = Double.parseDouble(percentage.substring(s));
                ConversionNumbers conNum = new ConversionNumbers(firstNumber, secondNumber);
                conversionNumbers.add(conNum);
            }
        }

        return conversionNumbers;
    }

    private int getSumOfPercentsForSubject(int SubjectID, DatabaseHandler db) {
        int sum = 0;
        assignments = db.getAllAssignments(false, "", "");
        List<Integer> num = new ArrayList<>();

        for (Assignments as : assignments) {
            if (!num.contains(as.getPercentWeightage()) && as.getSubjectID() == SubjectID) {
                num.add(as.getPercentWeightage());
            }
        }

        for (Integer n : num) {
            sum = n + sum;
        }
        return sum;
    }

    public int getAverageFeelingNumber(DatabaseHandler db, int SubjectID, Context c) {
        assignments = db.getAllAssignments(false, "", "");
        double totalFeelingNumber = 0.0;
        int num = 0;
        double average;

        for (Assignments a : assignments) {
            if (a.getSubjectID() == SubjectID) {
                totalFeelingNumber += a.getFeelingNumber();
                num++;
            }
        }

        average = totalFeelingNumber / num;
        prefs = c.getSharedPreferences("prefs", MODE_PRIVATE);
        return (int) round(average, prefs.getInt("decimalPlace", 0));
    }

    public double getScoreRequired(DatabaseHandler db, Subjects s, double desiredGrade, int givenPercentWeightage, Context c) {
        double scoreRequired;
        double SumOfScoresForSubjectWithoutPercent = 0;
        double SumOfScoresForSubjectForPercent = 0;
        //desiredGrade = 92.5;

        List<ScoresForPercents> sp = db.getScoreForPercent(s.getID());

        for (ScoresForPercents scp : sp) {
            if (scp.getPercent() == givenPercentWeightage) {
                SumOfScoresForSubjectForPercent += scp.getSum();
            } else {
                double num = scp.getSum() / percentWeightageCountForPercent(s, scp.getPercent(), db);
                double num2 = (double) scp.getPercent() / 100;
                SumOfScoresForSubjectWithoutPercent += num * num2;
            }
        }
        double score;

        if (SumOfScoresForSubjectWithoutPercent > 0) {
            double num = desiredGrade - SumOfScoresForSubjectWithoutPercent;
            double num2 = (num * (percentWeightageCountForPercent(s, givenPercentWeightage, db) + 1));
            double ScoreRequiredPW = num2 / ((double) givenPercentWeightage / 100);
            score = ScoreRequiredPW - SumOfScoresForSubjectForPercent;
        } else {
            double ScoreRequiredPW = desiredGrade * (percentWeightageCountForPercent(s, givenPercentWeightage, db) + 1);
            score = ScoreRequiredPW - SumOfScoresForSubjectForPercent;
        }

        if (score < 0) {
            scoreRequired = 0;
        } else {
            scoreRequired = score;
        }
        prefs = c.getSharedPreferences("prefs", MODE_PRIVATE);
        return round(scoreRequired, prefs.getInt("decimalPlace", 0));
    }
}
