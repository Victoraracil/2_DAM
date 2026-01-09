using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Aracil_Victor_EcoCharger.Models
{
    public enum ChargerType
    {
        Type2_Mennekes,
        CHAdeMO,
        CCS2_Combo,
        Schuko
    }
public class Charger
    {
        [Key]
        public int Id { get; set; }

        [Required]
        public int StationId { get; set; }

        [Required]
        public ChargerType Type { get; set; }

        [Range(1, 350)]
        public int MaxPower { get; set; }

        public bool IsOccupied { get; set; }

        // Relaciónes

        [ForeignKey("StationId")]
        public Station CargadorAsociado { get; set; }

        [InverseProperty("SesionAsociada")]
        public virtual ICollection<ChargingSession> ChargingSessions { get; set; } = new ObservableCollection<ChargingSession>();
    }
}
